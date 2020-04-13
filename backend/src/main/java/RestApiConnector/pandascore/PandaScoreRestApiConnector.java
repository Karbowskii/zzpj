package RestApiConnector.pandascore;


import RestApiConnector.ESportRestApi;

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
    public String getAllLeagues() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/tournaments");
        return this.getTextResponse(request);
    }

    @Override
    public String getAllSeries() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/series");
        return this.getTextResponse(request);
    }

    @Override
    public String getAllTournaments() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/tournaments");
        return this.getTextResponse(request);
    }

    @Override
    public String getAllMatches() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/matches");
        return this.getTextResponse(request);
    }

    @Override
    public String getAllTeams() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/teams");
        return this.getTextResponse(request);
    }

    @Override
    public String getAllPlayers() throws IOException, InterruptedException {
        HttpRequest request = this.createRequest("/players");
        return this.getTextResponse(request);
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
}
