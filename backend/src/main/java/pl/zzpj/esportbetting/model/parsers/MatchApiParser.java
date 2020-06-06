package pl.zzpj.esportbetting.model.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.zzpj.esportbetting.enumerate.MatchStatusEnum;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.Team;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class MatchApiParser {

    public static List<Match> parse(JSONArray jsonArray) throws JSONException {
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            matches.add(parse(jsonObject));
        }
        return matches;
    }

    public static Match parse(JSONObject json) throws JSONException {

        int id = json.getInt("id");
        int scoreA = 0;
        int scoreB = 0;
        boolean isFinished;
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        MatchStatusEnum status;

        JSONArray results = json.getJSONArray("results");
        if (results.length() == 2) {
            scoreA = results.getJSONObject(0).getInt("score");
            scoreB = results.getJSONObject(1).getInt("score");
        }

        JSONArray opponents = json.getJSONArray("opponents");
        JSONObject jsonTeamA = opponents.getJSONObject(0).getJSONObject("opponent");
        JSONObject jsonTeamB = opponents.getJSONObject(1).getJSONObject("opponent");

        Team teamA = TeamApiParser.parse(jsonTeamA);
        Team teamB = TeamApiParser.parse(jsonTeamB);

        String beginAt = json.getString("begin_at");
        if (beginAt != null && !beginAt.equals("null")) {
            try {
                startTime = LocalDateTime.ofInstant(Instant.parse(beginAt), ZoneId.systemDefault());
            } catch (DateTimeParseException e) {
                startTime = null;
            }
        }

        String endAt = json.getString("end_at");
        if (endAt != null && !endAt.equals("null")) {
            try {
                endTime = LocalDateTime.ofInstant(Instant.parse(endAt), ZoneId.systemDefault());
            } catch (DateTimeParseException e) {
                endTime = null;
            }
        }

        status = MatchStatusEnum.valueOf(json.getString("status").toUpperCase());

        return Match.builder()
                .realId(id)
                .realScoreA(scoreA)
                .realScoreB(scoreB)
                .teamA(teamA)
                .teamB(teamB)
                .startDate(startTime)
                .endDate(endTime)
                .status(status)
                .build();
    }
}
