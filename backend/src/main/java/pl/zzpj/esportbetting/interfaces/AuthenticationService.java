package pl.zzpj.esportbetting.interfaces;

import org.apache.commons.lang3.tuple.Pair;
import pl.zzpj.esportbetting.model.User;

public interface AuthenticationService {
    Pair<User, String> authenticate(String userName, String password);
}
