package pl.zzpj.esportbetting.interfaces;

import org.json.*;
import pl.zzpj.esportbetting.enumerate.GameEnum;

import java.io.IOException;

public interface ESportRestApi {
    JSONArray getAllLeagues(GameEnum game) throws IOException, InterruptedException;
    JSONArray getAllSeries(GameEnum game) throws IOException, InterruptedException;
    JSONArray getAllTournaments(GameEnum game) throws IOException, InterruptedException;
    JSONArray getAllMatches(GameEnum game) throws IOException, InterruptedException;
    JSONArray getAllTeams(GameEnum game) throws IOException, InterruptedException;
    JSONArray getAllPlayers(GameEnum game) throws IOException, InterruptedException;
}
