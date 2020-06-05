package pl.zzpj.esportbetting.response;

import lombok.Builder;
import lombok.Data;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.model.Level;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Level level;
    private int exp;
    private int coins;
    private List<AuthorityEnum> authorities;
    private boolean isActive;
}
