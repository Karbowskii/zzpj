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
import pl.zzpj.esportbetting.enumerate.MatchStatusEnum;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.Team;
import pl.zzpj.esportbetting.services.MatchService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MatchRestControllerTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    public MatchRestController matchRestController;

    public static MockMvc mockMvc;
    public static Team teamA;
    public static Team teamB;
    public static Match firstMatch;
    public static Match secondMatch;
    public static List<Match> matches;

    @BeforeClass
    public static void init(){
        teamA = Team.builder()
                .id(1)
                .name("testNameA")
                .url("testUrl")
                .matchesA(new ArrayList<>())
                .matchesB(new ArrayList<>())
                .build();

        teamB = Team.builder()
                .id(2)
                .name("testNameB")
                .url("testUrl")
                .matchesA(new ArrayList<>())
                .matchesB(new ArrayList<>())
                .build();

        firstMatch = Match.builder()
                .id(1)
                .startDate(LocalDateTime.parse("2020-06-02T00:00:00.00"))
                .endDate(null)
                .realId(1)
                .status(MatchStatusEnum.RUNNING)
                .teamA(teamA)
                .teamB(teamB)
                .stakeA(2)
                .stakeB(3)
                .realScoreA(3)
                .realScoreB(2)
                .comments(new ArrayList<>())
                .build();

        secondMatch = Match.builder()
                .id(2)
                .startDate(LocalDateTime.parse("2020-05-15T00:00:00.00"))
                .endDate(LocalDateTime.parse("2020-05-17T00:00:00.00"))
                .realId(1)
                .status(MatchStatusEnum.FINISHED)
                .teamA(teamA)
                .teamB(teamB)
                .stakeA(2)
                .stakeB(3)
                .realScoreA(3)
                .realScoreB(2)
                .comments(new ArrayList<>())
                .build();

        matches = Arrays.asList(firstMatch, secondMatch);
    }

    @Before
    public void setUp() {
        matchRestController = new MatchRestController(matchService);
        mockMvc = MockMvcBuilders.standaloneSetup(matchRestController).build();
    }

    @Test
    public void findAllTest() throws Exception {
        Mockito.when(matchService.findAll()).thenReturn(matches);
        mockMvc.perform(get("/matches")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is((int)matches.get(0).getId())))
                .andExpect(jsonPath("$.[0].realId", Matchers.is(matches.get(0).getRealId())))
                .andExpect(jsonPath("$.[0].status", Matchers.is(matches.get(0).getStatus().toString())))
                .andExpect(jsonPath("$.[0].teamA.id", Matchers.is((int)matches.get(0).getTeamA().getId())))
                .andExpect(jsonPath("$.[0].teamB.id", Matchers.is((int)matches.get(0).getTeamB().getId())))
                .andExpect(jsonPath("$.[0].stakeA", Matchers.is(matches.get(0).getStakeA())))
                .andExpect(jsonPath("$.[0].stakeB", Matchers.is(matches.get(0).getStakeB())))
                .andExpect(jsonPath("$.[0].realScoreA", Matchers.is(matches.get(0).getRealScoreA())))
                .andExpect(jsonPath("$.[0].realScoreB", Matchers.is(matches.get(0).getRealScoreB())))
                .andExpect(jsonPath("$.[0].comments", Matchers.is(matches.get(0).getComments())))

                .andExpect(jsonPath("$.[1].id", Matchers.is((int)matches.get(1).getId())))
                .andExpect(jsonPath("$.[1].realId", Matchers.is(matches.get(1).getRealId())))
                .andExpect(jsonPath("$.[1].status", Matchers.is(matches.get(1).getStatus().toString())))
                .andExpect(jsonPath("$.[1].teamA.id", Matchers.is((int)matches.get(1).getTeamA().getId())))
                .andExpect(jsonPath("$.[1].teamB.id", Matchers.is((int)matches.get(1).getTeamB().getId())))
                .andExpect(jsonPath("$.[1].stakeA", Matchers.is(matches.get(1).getStakeA())))
                .andExpect(jsonPath("$.[1].stakeB", Matchers.is(matches.get(1).getStakeB())))
                .andExpect(jsonPath("$.[1].realScoreA", Matchers.is(matches.get(1).getRealScoreA())))
                .andExpect(jsonPath("$.[1].realScoreB", Matchers.is(matches.get(1).getRealScoreB())))
                .andExpect(jsonPath("$.[1].comments", Matchers.is(matches.get(1).getComments())));
    }

}
