package pl.zzpj.esportbetting.model.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.zzpj.esportbetting.model.Match;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class MatchApiParser {
    public Match parse(JSONObject json) throws JSONException {

        int id = json.getInt("id");
        int scoreA = 0;
        int scoreB = 0;
        boolean isFinished;
        LocalDateTime startTime = null;

        JSONArray results = json.getJSONArray("results");
        if (results.length() == 2) {
            scoreA = results.getJSONObject(0).getInt("score");
            scoreB = results.getJSONObject(1).getInt("score");
        }

        // TODO fix zone in date
        String beginAt = json.getString("begin_at");
        if (beginAt != null) {
            try {
                startTime = LocalDateTime.parse(beginAt.substring(0, beginAt.length() - 1));
            } catch (DateTimeParseException e) {
                startTime = null;
            }
        }

        String endAt = json.getString("end_at");
        isFinished = endAt == "null" ? false : true;

        Match match = Match.builder()
                .realId(id)
                .realScoreA(scoreA)
                .realScoreB(scoreB)
                .startDate(startTime)
                .isFinished(isFinished)
                .build();

        return match;
    }
}
