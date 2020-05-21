package pl.zzpj.esportbetting.impl;

import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.interfaces.RankingService;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.UserRepository;
import pl.zzpj.esportbetting.response.RankedUserResponse;

import java.util.LinkedList;
import java.util.List;

@Service
public class RankingImpl implements RankingService {

    UserRepository userRepository;

    public RankingImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<RankedUserResponse> getRanking() {
        List<User> users =  this.userRepository.findAllByOrderByLevelIdDescExpDesc();
        List<RankedUserResponse> rankedUserResponses = new LinkedList<>();
        for (int i = 0; i< users.size(); i++) {
            rankedUserResponses.add(UserToRankedUserResponseConverter.convert(i + 1, users.get(i)));
        }
        return rankedUserResponses;
    }
}
