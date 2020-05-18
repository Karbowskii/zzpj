package pl.zzpj.esportbetting.interfaces;

import org.springframework.security.core.Authentication;
import pl.zzpj.esportbetting.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    User register(User user);

    User update(User user);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    User getUser(Authentication principal);
}
