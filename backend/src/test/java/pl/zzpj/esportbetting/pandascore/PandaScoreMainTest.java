package pl.zzpj.esportbetting.pandascore;

import pl.zzpj.esportbetting.enumerate.GameEnum;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import org.json.JSONException;

import java.io.*;

public class PandaScoreMainTest {

    public static void main(String args[]) throws IOException, InterruptedException, JSONException {
        File tokenFile = new File("backend", "token.txt");
        BufferedReader br = new BufferedReader(new FileReader(tokenFile));

        String token = br.readLine();
        br.close();

        ESportRestApi restApiConnector = new PandaScoreRestApiConnector();

        System.out.println(restApiConnector.getAllLeagues(GameEnum.DOTA2));
        System.out.println(restApiConnector.getAllSeries(GameEnum.DOTA2));
        System.out.println(restApiConnector.getAllTournaments(GameEnum.DOTA2));
        System.out.println(restApiConnector.getAllMatches(GameEnum.DOTA2));
        System.out.println(restApiConnector.getAllTeams(GameEnum.DOTA2));
        System.out.println(restApiConnector.getAllPlayers(GameEnum.DOTA2));
    }
}
