package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.model.User;

public interface UserContextService {
    User getAuthenticatedUser();
}
