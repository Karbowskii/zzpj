package pl.zzpj.esportbetting.model.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import pl.zzpj.esportbetting.enumerate.GameEnum;
import pl.zzpj.esportbetting.model.Team;
import pl.zzpj.esportbetting.mock.MockedPandaScoreConnector;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TeamApiParserTest {
    TeamApiParser parser;
    JSONArray jsonTeams;

    @Before
    public void setUp() throws IOException, InterruptedException {
        parser = new TeamApiParser();
        jsonTeams = new MockedPandaScoreConnector().getAllTeams(GameEnum.DOTA2);
    }

    @Test
    public void parseCorrectTeamTest() throws JSONException {
        // given
        JSONObject jsonCorrectTeam = jsonTeams.getJSONObject(0);

        // when
        Team team = parser.parse(jsonCorrectTeam);

        // test
        Team expectedTeam = Team.builder()
                .name("OverPower Esports")
                .url("https://cdn.pandascore.co/images/team/image/127397/123px_over_power_esportslogo_square.png")
                .build();

        assertEquals(expectedTeam.getName(), team.getName());
        assertEquals(expectedTeam.getUrl(), team.getUrl());
    }

    @Test(expected = JSONException.class)
    public void parseIncorrectTeamTest() throws JSONException {
        // given
        JSONObject jsonCorrectTeam = jsonTeams.getJSONObject(1);

        // when
        Team team = parser.parse(jsonCorrectTeam);
    }
}