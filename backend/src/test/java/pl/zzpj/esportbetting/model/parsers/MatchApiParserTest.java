package pl.zzpj.esportbetting.model.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import pl.zzpj.esportbetting.enumerate.GameEnum;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.mock.MockedPandaScoreConnector;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

public class MatchApiParserTest {
    MatchApiParser parser;
    JSONArray jsonArray;

    @Before
    public void setUp() {
        parser = new MatchApiParser();
        ESportRestApi connector = new MockedPandaScoreConnector();
        try {
            jsonArray = connector.getAllMatches(GameEnum.DOTA2);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseMatchCorrectTest() throws JSONException {
        // given
        // correct not started
        JSONObject jsonMatchNotStarted = jsonArray.getJSONObject(0);

        // when
        Match match = parser.parse(jsonMatchNotStarted);

        // test
        Match expectedMatch = Match.builder()
                .realId(560452)
                .realScoreA(0)
                .realScoreB(0)
                .isFinished(false)
                .startDate(LocalDateTime.ofInstant(Instant.parse("2020-05-11T19:00:00Z"), ZoneId.systemDefault()))
                .build();

        assertEquals(expectedMatch.getRealId(), match.getRealId());
        assertEquals(expectedMatch.getRealScoreA(), match.getRealScoreA());
        assertEquals(expectedMatch.getRealScoreB(), match.getRealScoreB());
        assertNotNull(match.getTeamA());
        assertNotNull(match.getTeamA());
        assertEquals(expectedMatch.getStartDate(), match.getStartDate());
        assertFalse(match.isFinished());

    }

    @Test
    public void parseMatchNullStartTest() throws JSONException {
        // given
        // no start date
        JSONObject jsonMatchWithNoStartDate = jsonArray.getJSONObject(1);

        // when
        Match match = parser.parse(jsonMatchWithNoStartDate);

        // test
        Match expectedMatch = Match.builder()
                .realId(34512)
                .realScoreA(0)
                .realScoreB(0)
                .isFinished(false)
                .startDate(null)
                .build();

        assertEquals(expectedMatch.getRealId(), match.getRealId());
        assertEquals(expectedMatch.getRealScoreA(), match.getRealScoreA());
        assertEquals(expectedMatch.getRealScoreB(), match.getRealScoreB());
        assertNotNull(match.getTeamA());
        assertNotNull(match.getTeamA());
        assertNull(match.getStartDate());
        assertFalse(match.isFinished());
    }

    @Test
    public void parseMatchFinishedTest() throws JSONException {
        // given
        // finished
        JSONObject jsonMatchFinished = jsonArray.getJSONObject(2);

        // when
        Match match = parser.parse(jsonMatchFinished);

        // test
        Match expectedMatch = Match.builder()
                .realId(65474)
                .realScoreA(0)
                .realScoreB(1)
                .isFinished(true)
                .startDate(LocalDateTime.ofInstant(Instant.parse("2020-05-11T19:00:00Z"),
                        ZoneId.systemDefault()))
                .build();

        assertEquals(expectedMatch.getRealId(), match.getRealId());
        assertEquals(expectedMatch.getRealScoreA(), match.getRealScoreA());
        assertEquals(expectedMatch.getRealScoreB(), match.getRealScoreB());
        assertNotNull(match.getTeamA());
        assertNotNull(match.getTeamA());
        assertEquals(expectedMatch.getStartDate(), match.getStartDate());
        assertTrue(match.isFinished());
    }

    @Test(expected = JSONException.class)
    public void parseMatchExceptionTest() throws JSONException {
        // given
        JSONObject jsonNotCorrect = new JSONObject("{Hello world}");

        // when
        Match match = parser.parse(jsonNotCorrect);
    }
}