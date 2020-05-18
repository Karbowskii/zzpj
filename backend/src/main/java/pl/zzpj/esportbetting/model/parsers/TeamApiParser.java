package pl.zzpj.esportbetting.model.parsers;

import org.json.JSONException;
import org.json.JSONObject;
import pl.zzpj.esportbetting.model.Team;

public class TeamApiParser {

    public static Team parse(JSONObject json) throws JSONException {
        int id = json.getInt("id");
        String name = json.getString("name");
        String image_url = json.getString("image_url");

        Team team = Team.builder()
                .id(id)
                .name(name)
                .url(image_url)
                .build();
        return team;
    }
}
