package pl.zzpj.esportbetting.impl;

import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.interfaces.RankingService;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.UserRepository;
import pl.zzpj.esportbetting.response.RankedUserResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingImpl implements RankingService {

    private UserRepository userRepository;
    private int topUsers;

    public RankingImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        topUsers = 10;
    }

    @Override
    public List<RankedUserResponse> getRanking() {
        List<User> users =  this.userRepository.findAllByOrderByLevelIdDescExpDesc();
        List<RankedUserResponse> rankedUserResponses = new LinkedList<>();
        for (int i = 0; i< users.size(); i++) {
            rankedUserResponses.add(UserToRankedUserResponseConverter.convert(i + 1, users.get(i)));
        }
        return rankedUserResponses.stream().limit(topUsers).collect(Collectors.toList());
    }

    @Override
    public RankedUserResponse getUserRank(String username) {
        return this.getRanking().stream()
                .filter(user -> user.getUser().getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User %s not found in database",
                        username)));
    }
}
