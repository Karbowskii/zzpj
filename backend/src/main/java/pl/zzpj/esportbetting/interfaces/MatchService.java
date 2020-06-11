package pl.zzpj.esportbetting.interfaces;

import org.json.JSONException;
import pl.zzpj.esportbetting.model.Match;

import java.io.IOException;
import java.util.List;

public interface MatchService {
    List<Match> findAll();

    void checkAndUpdateMatches() throws IOException, InterruptedException, JSONException;

    Match findById(long matchId);
}
