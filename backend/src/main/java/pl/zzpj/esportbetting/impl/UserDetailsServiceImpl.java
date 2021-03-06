package pl.zzpj.esportbetting.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.enumerate.DetailedFinishedStatusEnum;
import pl.zzpj.esportbetting.enumerate.MatchStatusEnum;
import pl.zzpj.esportbetting.exception.AlreadyTakenException;
import pl.zzpj.esportbetting.exception.IllegalActionException;
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.exception.ValidationException;
import pl.zzpj.esportbetting.interfaces.UserService;
import pl.zzpj.esportbetting.model.*;
import pl.zzpj.esportbetting.repos.AuthorityRepository;
import pl.zzpj.esportbetting.repos.LevelRepository;
import pl.zzpj.esportbetting.repos.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private static final int STARTER_COINS = 100;
    private static final int WEEKLY_COINS = 10;

    private final UserRepository userRepository;
    private final LevelRepository levelRepository;
    private final AuthorityRepository authorityRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository,
                                  LevelRepository levelRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.levelRepository = levelRepository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        return user.map(UserDetailsImpl::new).orElseThrow(
                () -> new ObjectNotFoundException("Not found user with username: " + userName));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Not found user with id: " + id));
    }

    @Scheduled(cron = "0 0 10 ? * Mon")
    public void giveWeeklyCoins(){
        for(User user: userRepository.findAll()){
            user.addCoins(WEEKLY_COINS);
        }
    }

    @Override
    public User register(User user) {
        Optional<Authority> authority = authorityRepository.findByName(AuthorityEnum.ROLE_USER);
        if (authority.isPresent()) {
            user.addAuthority(authority.get());
        }
        if (Stream.of(user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstName(),
                user.getLastName()).anyMatch(""::equals)) {
            throw new IllegalActionException("Invalid form");
        } else if (existsByUserName(user.getUsername())) {
            throw new AlreadyTakenException("Username is taken");
        } else if (existsByEmail(user.getEmail())) {
            throw new AlreadyTakenException("Email is taken");
        } else {
            setUserToStartingUser(user);
            return userRepository.save(user);
        }
    }

    private void setUserToStartingUser(User user) {
        Optional<Level> firstLevel = levelRepository.findById(1L);
        if (!firstLevel.isPresent()) {
            throw new ObjectNotFoundException("Not found level: " + firstLevel.get().getId());
        }
        user.setExp(0);
        user.setLevel(firstLevel.get());
        user.setCoins(STARTER_COINS);
    }

    @Override
    public User update(User user) {
        Optional<User> userFound = Optional.of(userRepository.getOne(user.getId()));
        if (!userFound.isPresent()) {
            throw new ObjectNotFoundException("Not found user with id: " + user.getId());
        }
        if (user.getUsername() != null && !user.getUsername().equals(userFound.get().getUsername())) {
            throw new IllegalActionException("Changing username is forbidden");
        }
        if (user.getEmail() != null) {
            userFound.get().setEmail(user.getEmail());
        }
        if (user.getFirstName() != null) {
            userFound.get().setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userFound.get().setLastName(user.getLastName());
        }
        if (user.getIsActive() != null) {
            userFound.get().setIsActive(user.getIsActive());
        }
        return userRepository.saveAndFlush(userFound.get());

    }

    public Boolean existsByUserName(String userName) {
        return this.userRepository.existsByUsername(userName);
    }

    public Boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User getUser(Authentication principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) principal.getPrincipal();
        return UserDetailsImplToUserConverter.convert(userDetails);
    }

    @Override
    public Statistics getUserStats(User user) {
        List<Bet> userFinishedBets = user.getBets().stream()
                .filter(b -> b.getMatch().getStatus() == MatchStatusEnum.FINISHED)
                .collect(Collectors.toList());

        int goodBets = 0;
        int badBets = 0;
        int earnedCoins = 0;
        int lostCoins = 0;

        for (Bet bet : userFinishedBets) {
            DetailedFinishedStatusEnum winnerTeam = bet.getMatch().getWhichTeamWon();
            if ((winnerTeam.equals(DetailedFinishedStatusEnum.A_WIN) && bet.isSelectedA()) ||
                    (winnerTeam.equals(DetailedFinishedStatusEnum.B_WIN) && !bet.isSelectedA())) {
                float stake = winnerTeam.equals(DetailedFinishedStatusEnum.A_WIN) ?
                        bet.getMatch().getStakeA() : bet.getMatch().getStakeB();
                earnedCoins += Math.round(bet.getCoins() * stake);
                goodBets++;
            } else {
                lostCoins += bet.getCoins();
                badBets++;
            }
        }
        return new Statistics(goodBets, badBets, earnedCoins, lostCoins);
    }

    @Override
    public User changePassword(User user, String oldPassword, String newPassword, PasswordEncoder passwordEncoder) {
        Optional<User> userFound = Optional.of(userRepository.getOne(user.getId()));
        if (!userFound.isPresent()) {
            throw new ObjectNotFoundException("Not found user with id: " + user.getId());
        }

        if (!passwordEncoder.matches(oldPassword, userFound.get().getPassword())) {
            throw new ValidationException("Incorrect old password");
        }

        userFound.get().setPassword(passwordEncoder.encode(newPassword));

        return userRepository.saveAndFlush(userFound.get());
    }

    @Override
    public User applyPatchToCustomer (JsonPatch patch, User targetUser) throws JsonPatchException,
            JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }
}
