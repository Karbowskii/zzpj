package pl.zzpj.esportbetting.pandascore;

import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import org.json.JSONException;

import java.io.*;

public class PandaScoreMainTest {

    public static void main(String args[]) throws IOException, InterruptedException, JSONException {
        File tokenFile = new File("backend", "token.txt");
        BufferedReader br = new BufferedReader(new FileReader(tokenFile));

        String token = br.readLine();
        br.close();

        ESportRestApi restApiConnector = new PandaScoreRestApiConnector(PandaScoreRestApiConnector.Game.DOTA2, token);

        System.out.println(restApiConnector.getAllLeagues());
        System.out.println(restApiConnector.getAllSeries());
        System.out.println(restApiConnector.getAllTournaments());
        System.out.println(restApiConnector.getAllMatches());
        System.out.println(restApiConnector.getAllTeams());
        System.out.println(restApiConnector.getAllPlayers());
    }
}
