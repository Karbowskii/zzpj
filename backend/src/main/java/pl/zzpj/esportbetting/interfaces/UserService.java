package pl.zzpj.esportbetting.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    User changePassword(User user, String oldPassword, String newPassword, PasswordEncoder passwordEncoder);

    User applyPatchToCustomer(JsonPatch patch, User targetCustomer) throws JsonPatchException, JsonProcessingException;
}
