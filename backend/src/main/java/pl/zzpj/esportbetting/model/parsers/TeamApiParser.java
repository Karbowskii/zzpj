package pl.zzpj.esportbetting.model.parsers;

import org.json.JSONException;
import org.json.JSONObject;
import pl.zzpj.esportbetting.model.Team;

public class TeamApiParser {

    public static Team parse(JSONObject json) throws JSONException {
        String name = json.getString("name");
        String image_url = json.getString("image_url");

        return Team.builder()
                .name(name)
                .url(image_url)
                .build();
    }
}
