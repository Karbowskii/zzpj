package pl.zzpj.esportbetting.mock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.zzpj.esportbetting.enumerate.GameEnum;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;

import java.io.IOException;

public class MockedPandaScoreConnector implements ESportRestApi {
    @Override
    public JSONArray getAllLeagues(GameEnum game) {
        return null;
    }

    @Override
    public JSONArray getAllSeries(GameEnum game) {
        return null;
    }

    @Override
    public JSONArray getAllTournaments(GameEnum game) {
        return null;
    }

    @Override
    public JSONArray getAllMatches(GameEnum game) {
        JSONArray jsonArray = new JSONArray();
        try {
            // not started
            jsonArray.put(new JSONObject("{\n" +
                    "\t\"live_embed_url\": null,\n" +
                    "\t\"end_at\": null,\n" +
                    "\t\"winner_id\": null,\n" +
                    "\t\"begin_at\": \"2020-05-11T19:00:00Z\",\n" +
                    "\t\"number_of_games\": 3,\n" +
                    "\t\"id\": 560452,\n" +
                    "\t\"modified_at\": \"2020-05-08T14:32:33Z\",\n" +
                    "\t\"results\": [{\n" +
                    "\t\t\"score\": 0,\n" +
                    "\t\t\"team_id\": 126560\n" +
                    "\t}, {\n" +
                    "\t\t\"score\": 0,\n" +
                    "\t\t\"team_id\": 3230\n" +
                    "\t}],\n" +
                    "\t\"rescheduled\": false,\n" +
                    "\t\"opponents\": [{\n" +
                    "\t\t\"opponent\": {\n" +
                    "\t\t\t\"acronym\": null,\n" +
                    "\t\t\t\"image_url\": \"https:\\/\\/cdn.pandascore.co\\/images\\/team\\/image\\/126560\\/9949.png\",\n" +
                    "\t\t\t\"name\": \"TheDice\",\n" +
                    "\t\t\t\"location\": null,\n" +
                    "\t\t\t\"id\": 126560,\n" +
                    "\t\t\t\"modified_at\": \"2020-05-08T14:31:02Z\",\n" +
                    "\t\t\t\"slug\": \"the-dice\"\n" +
                    "\t\t},\n" +
                    "\t\t\"type\": \"Team\"\n" +
                    "\t}, {\n" +
                    "\t\t\"opponent\": {\n" +
                    "\t\t\t\"acronym\": null,\n" +
                    "\t\t\t\"image_url\": \"https:\\/\\/cdn.pandascore.co\\/images\\/team\\/image\\/3230\\/LDLC_FEMALE.png\",\n" +
                    "\t\t\t\"name\": \"LDLC\",\n" +
                    "\t\t\t\"location\": \"FR\",\n" +
                    "\t\t\t\"id\": 3230,\n" +
                    "\t\t\t\"modified_at\": \"2020-05-08T14:30:34Z\",\n" +
                    "\t\t\t\"slug\": \"ldlc-cs-go\"\n" +
                    "\t\t},\n" +
                    "\t\t\"type\": \"Team\"\n" +
                    "\t}],\n" +
                    "\t\"forfeit\": false,\n" +
                    "\t\"draw\": false,\n" +
                    "\t\"winner\": null,\n" +
                    "\t\"name\": \"Semifinal 2: TheDice vs LDLC\",\n" +
                    "\t\"status\": \"running\"\n" +
                    "}"));

            // no start date
            jsonArray.put(new JSONObject("{\n" +
                    "\t\"live_embed_url\": null,\n" +
                    "\t\"end_at\": null,\n" +
                    "\t\"winner_id\": null,\n" +
                    "\t\"begin_at\": null,\n" +
                    "\t\"number_of_games\": 3,\n" +
                    "\t\"id\": 34512,\n" +
                    "\t\"modified_at\": \"2020-05-08T14:32:33Z\",\n" +
                    "\t\"results\": [{\n" +
                    "\t\t\"score\": 0,\n" +
                    "\t\t\"team_id\": 126560\n" +
                    "\t}, {\n" +
                    "\t\t\"score\": 0,\n" +
                    "\t\t\"team_id\": 3230\n" +
                    "\t}],\n" +
                    "\t\"rescheduled\": false,\n" +
                    "\t\"opponents\": [{\n" +
                    "\t\t\"opponent\": {\n" +
                    "\t\t\t\"acronym\": null,\n" +
                    "\t\t\t\"image_url\": \"https:\\/\\/cdn.pandascore.co\\/images\\/team\\/image\\/126560\\/9949.png\",\n" +
                    "\t\t\t\"name\": \"TheDice\",\n" +
                    "\t\t\t\"location\": null,\n" +
                    "\t\t\t\"id\": 126560,\n" +
                    "\t\t\t\"modified_at\": \"2020-05-08T14:31:02Z\",\n" +
                    "\t\t\t\"slug\": \"the-dice\"\n" +
                    "\t\t},\n" +
                    "\t\t\"type\": \"Team\"\n" +
                    "\t}, {\n" +
                    "\t\t\"opponent\": {\n" +
                    "\t\t\t\"acronym\": null,\n" +
                    "\t\t\t\"image_url\": \"https:\\/\\/cdn.pandascore.co\\/images\\/team\\/image\\/3230\\/LDLC_FEMALE.png\",\n" +
                    "\t\t\t\"name\": \"LDLC\",\n" +
                    "\t\t\t\"location\": \"FR\",\n" +
                    "\t\t\t\"id\": 3230,\n" +
                    "\t\t\t\"modified_at\": \"2020-05-08T14:30:34Z\",\n" +
                    "\t\t\t\"slug\": \"ldlc-cs-go\"\n" +
                    "\t\t},\n" +
                    "\t\t\"type\": \"Team\"\n" +
                    "\t}],\n" +
                    "\t\"forfeit\": false,\n" +
                    "\t\"draw\": false,\n" +
                    "\t\"winner\": null,\n" +
                    "\t\"name\": \"Semifinal 2: TheDice vs LDLC\",\n" +
                    "\t\"status\": \"canceled\"\n" +
                    "}"));

            // finished
            jsonArray.put(new JSONObject("{\n" +
                    "\t\"live_embed_url\": null,\n" +
                    "\t\"end_at\": \"2020-05-11T21:30:00Z\",\n" +
                    "\t\"winner_id\": null,\n" +
                    "\t\"begin_at\": \"2020-05-11T19:00:00Z\",\n" +
                    "\t\"number_of_games\": 3,\n" +
                    "\t\"id\": 65474,\n" +
                    "\t\"modified_at\": \"2020-05-08T14:32:33Z\",\n" +
                    "\t\"results\": [{\n" +
                    "\t\t\"score\": 0,\n" +
                    "\t\t\"team_id\": 126560\n" +
                    "\t}, {\n" +
                    "\t\t\"score\": 1,\n" +
                    "\t\t\"team_id\": 3230\n" +
                    "\t}],\n" +
                    "\t\"rescheduled\": false,\n" +
                    "\t\"opponents\": [{\n" +
                    "\t\t\"opponent\": {\n" +
                    "\t\t\t\"acronym\": null,\n" +
                    "\t\t\t\"image_url\": \"https:\\/\\/cdn.pandascore.co\\/images\\/team\\/image\\/126560\\/9949.png\",\n" +
                    "\t\t\t\"name\": \"TheDice\",\n" +
                    "\t\t\t\"location\": null,\n" +
                    "\t\t\t\"id\": 126560,\n" +
                    "\t\t\t\"modified_at\": \"2020-05-08T14:31:02Z\",\n" +
                    "\t\t\t\"slug\": \"the-dice\"\n" +
                    "\t\t},\n" +
                    "\t\t\"type\": \"Team\"\n" +
                    "\t}, {\n" +
                    "\t\t\"opponent\": {\n" +
                    "\t\t\t\"acronym\": null,\n" +
                    "\t\t\t\"image_url\": \"https:\\/\\/cdn.pandascore.co\\/images\\/team\\/image\\/3230\\/LDLC_FEMALE.png\",\n" +
                    "\t\t\t\"name\": \"LDLC\",\n" +
                    "\t\t\t\"location\": \"FR\",\n" +
                    "\t\t\t\"id\": 3230,\n" +
                    "\t\t\t\"modified_at\": \"2020-05-08T14:30:34Z\",\n" +
                    "\t\t\t\"slug\": \"ldlc-cs-go\"\n" +
                    "\t\t},\n" +
                    "\t\t\"type\": \"Team\"\n" +
                    "\t}],\n" +
                    "\t\"forfeit\": false,\n" +
                    "\t\"draw\": false,\n" +
                    "\t\"winner\": null,\n" +
                    "\t\"name\": \"Semifinal 2: TheDice vs LDLC\",\n" +
                    "\t\"status\": \"finished\"\n" +
                    "}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    @Override
    public JSONArray getAllTeams(GameEnum game) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(new JSONObject("{\n" +
                    "\"id\": 127397,\n" +
                    "\"name\": \"OverPower Esports\",\n" +
                    "\"image_url\": \"https:\\/\\/cdn.pandascore.co\\/images\\/team\\/image\\/127397\\/123px_over_power_esportslogo_square.png\"\n" +
                    "}"));
            jsonArray.put(new JSONObject("{\n" +
                    "\"not_id\": 127397,\n" +
                    "\"not_name\": \"OverPower Esports\",\n" +
                    "\"not_image_url\": \"https:\\/\\/cdn.pandascore.co\\/images\\/team\\/image\\/127397\\/123px_over_power_esportslogo_square.png\"\n" +
                    "}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public JSONArray getAllPlayers(GameEnum game) {
        return null;
    }

    @Override
    public JSONArray getAllPastMatches(GameEnum game){return null;}

    @Override
    public JSONArray getAllUpcomingMatches(GameEnum game){return null;}
}
