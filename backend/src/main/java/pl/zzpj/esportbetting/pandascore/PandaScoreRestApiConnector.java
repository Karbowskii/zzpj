package pl.zzpj.esportbetting.pandascore;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.zzpj.esportbetting.enumerate.GameEnum;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component("eSportRestApi")
public class PandaScoreRestApiConnector implements ESportRestApi {

    @Value("${esportbetting.pandascore.token}")
    private String token;
    private final String baseUrl = "https://api.pandascore.co/";
    private final HttpClient httpClient = HttpClient.newBuilder().build();


    @Override
    public JSONArray getAllLeagues(GameEnum game) throws IOException, InterruptedException {
        HttpRequest request = this.createRequest(game, "/tournaments");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllSeries(GameEnum game) throws IOException, InterruptedException {
        HttpRequest request = this.createRequest(game, "/series");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllTournaments(GameEnum game) throws IOException, InterruptedException {
        HttpRequest request = this.createRequest(game, "/tournaments");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllMatches(GameEnum game) throws IOException, InterruptedException {
        HttpRequest request = this.createRequest(game, "/matches");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllPastMatches(GameEnum game) throws IOException, InterruptedException {
        HttpRequest request = this.createRequest(game, "/matches/past", "&sort=-begin_at");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllUpcomingMatches(GameEnum game) throws IOException, InterruptedException {
        HttpRequest request = this.createRequest(game, "/matches/upcoming", "&sort=begin_at");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllTeams(GameEnum game) throws IOException, InterruptedException {
        HttpRequest request = this.createRequest(game, "/teams");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllPlayers(GameEnum game) throws IOException, InterruptedException {
        HttpRequest request = this.createRequest(game, "/players");
        return this.processRequest(request);
    }

    private HttpRequest createRequest(GameEnum game, String option) {
        HttpRequest request = HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create(this.baseUrl + game.toString() + option + "?token=" + this.token))
                                .build();
        return request;
    }

    private HttpRequest createRequest(GameEnum game, String option, String params) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(this.baseUrl + game.toString() + option + "?token=" + this.token + params))
                .build();
        return request;
    }

    private String getTextResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Error while fetching data. Status code: " + response.statusCode());
        }
        String res = response.body().toString();
        return res;
    }

    private JSONArray stringToJson(String jsonString) {
        try {
            return new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray processRequest(HttpRequest request) throws IOException, InterruptedException {
        return this.stringToJson(this.getTextResponse(request));
    }
}
