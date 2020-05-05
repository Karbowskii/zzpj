package pl.zzpj.esportbetting.interfaces;

import org.springframework.security.core.Authentication;

public interface Jwt {

    String generateJwtToken(Authentication authentication);

    String getUserNameFromJwtToken(String token);

    boolean isJwtTokenValid(String authToken);

}
