package pl.zzpj.esportbetting.model.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.Team;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

public class MatchApiParser {
    private final TeamApiParser teamApiParser = new TeamApiParser();

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

        JSONArray opponents = json.getJSONArray("opponents");
        JSONObject jsonTeamA = opponents.getJSONObject(0).getJSONObject("opponent");
        JSONObject jsonTeamB = opponents.getJSONObject(1).getJSONObject("opponent");

        Team teamA = this.teamApiParser.parse(jsonTeamA);
        Team teamB = this.teamApiParser.parse(jsonTeamB);

        String beginAt = json.getString("begin_at");
        if (beginAt != null && !beginAt.equals("null")) {
            try {
                startTime = LocalDateTime.ofInstant(Instant.parse(beginAt), ZoneId.systemDefault());
            } catch (DateTimeParseException e) {
                startTime = null;
            }
        }

        String endAt = json.getString("end_at");
        isFinished = !endAt.equals("null");

        Match match = Match.builder()
                .realId(id)
                .realScoreA(scoreA)
                .realScoreB(scoreB)
                .teamA(teamA)
                .teamB(teamB)
                .startDate(startTime)
                .isFinished(isFinished)
                .build();

        return match;
    }
}
