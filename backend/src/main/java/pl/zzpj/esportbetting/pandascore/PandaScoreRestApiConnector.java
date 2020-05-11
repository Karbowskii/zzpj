package pl.zzpj.esportbetting.pandascore;


import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PandaScoreRestApiConnector implements ESportRestApi {
    
    enum Game {
        LEAGUE_OF_LEGENDS {
            public String toString() {
                return "lol";
            }
        },
        CS_GO {
            public String toString() {
                return "csgo";
            }
        },
        DOTA2 {
            public String toString() {
                return "dota2";
            }
        },
        OVERWATCH {
            public String toString() {
                return "ow";
            }
        },

    }

    private final String token;
    private final String baseUrl;
    private final String url;
    private final HttpClient httpClient;

    public PandaScoreRestApiConnector(Game game, String token) {
        this.baseUrl = "https://api.pandascore.co/";
        this.url = this.baseUrl + game;
        this.token = token;
        this.httpClient = HttpClient.newBuilder().build();
    }

    @Override
    public JSONArray getAllLeagues() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/tournaments");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllSeries() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/series");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllTournaments() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/tournaments");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllMatches() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/matches");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllTeams() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/teams");
        return this.processRequest(request);
    }

    @Override
    public JSONArray getAllPlayers() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/players");
        return this.processRequest(request);
    }

    private HttpRequest createRequest(String option) {
        HttpRequest request = HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create(this.url + option + "?token=" + this.token))
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