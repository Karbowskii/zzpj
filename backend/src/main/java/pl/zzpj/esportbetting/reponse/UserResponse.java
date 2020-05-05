package pl.zzpj.esportbetting.reponse;

import lombok.Builder;
import lombok.Data;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private long levelId;
    private int exp;
    private int coins;
    private List<AuthorityEnum> authorities;
    private boolean isActive;
}
