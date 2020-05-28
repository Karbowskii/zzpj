package pl.zzpj.esportbetting.rest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.impl.UserToRankedUserResponseConverter;
import pl.zzpj.esportbetting.interfaces.RankingService;
import pl.zzpj.esportbetting.interfaces.UserContextService;
import pl.zzpj.esportbetting.model.Authority;
import pl.zzpj.esportbetting.model.Level;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.response.RankedUserResponse;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class RankingRestControllerTest {

    @Mock
    private RankingService rankingService;

    @Mock
    private UserContextService userContextService;

    @InjectMocks
    public RankingRestController rankingRestController;

    public static MockMvc mockMvc;

    public static List<User> users;
    public static User userLevel1;
    public static User userLevel3Exp100;
    public static User userLevel3Exp1;
    public static User userLevel4;
    public static User userLevel5;

    @BeforeClass
    public static void init() {
        Set<Authority> authorityRoleUser = new HashSet<>();
        authorityRoleUser.add(Authority.builder().name(AuthorityEnum.ROLE_USER).build());
        userLevel1 = User.builder()
                .id(1)
                .username("username1")
                .password("hashedPassword1")
                .email("email1@email")
                .firstName("Piotr")
                .lastName("Wroblewski")
                .level(Level.builder().id(1).build())
                .exp(1)
                .coins(100)
                .isActive(true)
                .authorities(authorityRoleUser)
                .build();
        userLevel3Exp100 = User.builder()
                .id(2)
                .username("username2")
                .password("hashedPassword2")
                .email("email2@email")
                .firstName("Artur")
                .lastName("Karbowski")
                .level(Level.builder().id(3).build())
                .exp(100)
                .coins(100)
                .isActive(true)
                .authorities(authorityRoleUser)
                .build();
        userLevel3Exp1 = User.builder()
                .id(3)
                .username("username3")
                .password("hashedPassword3")
                .email("email3@email")
                .firstName("Adrian")
                .lastName("Zielinski")
                .level(Level.builder().id(3).build())
                .exp(1)
                .coins(400)
                .isActive(true)
                .authorities(authorityRoleUser)
                .build();
        userLevel4 = User.builder()
                .id(4)
                .username("username4")
                .password("hashedPassword4")
                .email("email4@email")
                .firstName("Michal")
                .lastName("Pesko")
                .level(Level.builder().id(4).build())
                .exp(1)
                .coins(100)
                .isActive(true)
                .authorities(authorityRoleUser)
                .build();
        userLevel5 = User.builder()
                .id(5)
                .username("username5")
                .password("hashedPassword5")
                .email("email5@email")
                .firstName("Sylwester")
                .lastName("Dobrawski")
                .level(Level.builder().id(5).build())
                .exp(1)
                .coins(100)
                .isActive(true)
                .authorities(authorityRoleUser)
                .build();

        users = new LinkedList<>();
        users.add(userLevel1);
        users.add(userLevel3Exp100);
        users.add(userLevel3Exp1);
        users.add(userLevel4);
        users.add(userLevel5);
    }

    @Before
    public void setUp() {
        rankingRestController = new RankingRestController(rankingService, userContextService);
        mockMvc = MockMvcBuilders.standaloneSetup(rankingRestController).build();
    }

    @Test
    public void getUserRankTest() throws Exception {
        String username = "username3";
        RankedUserResponse rankResponse = UserToRankedUserResponseConverter.convert(4, userLevel3Exp1);
        Mockito.when(rankingService.getUserRank(username)).thenReturn(rankResponse);

        mockMvc.perform(get("/ranking/{username}", userLevel3Exp1.getUsername())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.place",
                        Matchers.is(rankResponse.getPlace())))
                .andExpect(jsonPath("$.user.username",
                        Matchers.is(userLevel3Exp1.getUsername())));
    }

    @Test
    public void getRankingTest() throws Exception {
        RankedUserResponse place1 = UserToRankedUserResponseConverter.convert(1, userLevel5);
        RankedUserResponse place2 = UserToRankedUserResponseConverter.convert(2, userLevel4);
        RankedUserResponse place3 = UserToRankedUserResponseConverter.convert(3, userLevel3Exp100);
        RankedUserResponse place4 = UserToRankedUserResponseConverter.convert(4, userLevel3Exp1);
        RankedUserResponse place5 = UserToRankedUserResponseConverter.convert(5, userLevel1);
        List<RankedUserResponse> ranking = new LinkedList<>();
        ranking.add(place1);
        ranking.add(place2);
        ranking.add(place3);
        ranking.add(place4);
        ranking.add(place5);

        Mockito.when(rankingService.getRanking()).thenReturn(ranking);

        mockMvc.perform(get("/ranking/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place",
                        Matchers.is(place1.getPlace())))
                .andExpect(jsonPath("$.[0].user.username",
                        Matchers.is(place1.getUser().getUsername())))
                .andExpect(jsonPath("$.[1].place",
                        Matchers.is(place2.getPlace())))
                .andExpect(jsonPath("$.[1].user.username",
                        Matchers.is(place2.getUser().getUsername())))
                .andExpect(jsonPath("$.[2].place",
                        Matchers.is(place3.getPlace())))
                .andExpect(jsonPath("$.[2].user.username",
                        Matchers.is(place3.getUser().getUsername())))
                .andExpect(jsonPath("$.[3].place",
                        Matchers.is(place4.getPlace())))
                .andExpect(jsonPath("$.[3].user.username",
                        Matchers.is(place4.getUser().getUsername())))
                .andExpect(jsonPath("$.[4].place",
                        Matchers.is(place5.getPlace())))
                .andExpect(jsonPath("$.[4].user.username",
                        Matchers.is(place5.getUser().getUsername())));
    }
}