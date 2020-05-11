package pl.zzpj.esportbetting.model.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.pandascore.MockedPandaScoreConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MatchApiParserTest {
    MatchApiParser parser;
    JSONArray jsonArray;

    @Before
    public void setUp() {
        parser = new MatchApiParser();
        ESportRestApi connector = new MockedPandaScoreConnector();
        try {
            jsonArray = connector.getAllMatches();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
        assertEquals(560452, match.getRealId());
        assertEquals(0, match.getRealScoreA());
        assertEquals(0, match.getRealScoreB());
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-05-11T19:00:00");
        assertEquals(expectedStartDate, match.getStartDate());
        assertEquals(false, match.isFinished());

    }

    @Test
    public void parseMatchNullStartTest() throws JSONException {
        // given
        // no start date
        JSONObject jsonMatchWithNoStartDate = jsonArray.getJSONObject(1);

        // when
        Match match = parser.parse(jsonMatchWithNoStartDate);

        // test
        assertEquals(34512, match.getRealId());
        assertEquals(0, match.getRealScoreA());
        assertEquals(0, match.getRealScoreB());
        assertEquals(null, match.getStartDate());
        assertEquals(false, match.isFinished());
    }

    @Test
    public void parseMatchFinishedTest() throws JSONException {
        // given
        // finished
        JSONObject jsonMatchFinished = jsonArray.getJSONObject(2);

        // when
        Match match = parser.parse(jsonMatchFinished);

        // test
        assertEquals(65474, match.getRealId());
        assertEquals(0, match.getRealScoreA());
        assertEquals(1, match.getRealScoreB());
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-05-11T19:00:00");
        assertEquals(expectedStartDate, match.getStartDate());
        assertEquals(true, match.isFinished());
    }

    @Test(expected = JSONException.class)
    public void parseMatchExceptionTest() throws JSONException {
        // given
        JSONObject jsonNotCorrect = new JSONObject("{Hello world}");

        // when
        Match match = parser.parse(jsonNotCorrect);
    }
}