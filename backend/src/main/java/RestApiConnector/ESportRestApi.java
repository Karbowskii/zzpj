package RestApiConnector;

import java.io.IOException;

public interface ESportRestApi {
    String getAllLeagues() throws IOException, InterruptedException;
    String getAllSeries() throws IOException, InterruptedException;
    String getAllTournaments() throws IOException, InterruptedException;
    String getAllMatches() throws IOException, InterruptedException;
    String getAllTeams() throws IOException, InterruptedException;
    String getAllPlayers() throws IOException, InterruptedException;
}
