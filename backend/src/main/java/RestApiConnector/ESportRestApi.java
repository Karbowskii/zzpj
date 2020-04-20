package RestApiConnector;

import org.json.*;

import java.io.IOException;

public interface ESportRestApi {
    JSONArray getAllLeagues() throws IOException, InterruptedException;
    JSONArray getAllSeries() throws IOException, InterruptedException;
    JSONArray getAllTournaments() throws IOException, InterruptedException;
    JSONArray getAllMatches() throws IOException, InterruptedException;
    JSONArray getAllTeams() throws IOException, InterruptedException;
    JSONArray getAllPlayers() throws IOException, InterruptedException;
}
