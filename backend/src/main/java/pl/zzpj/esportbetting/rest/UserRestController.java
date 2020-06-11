package pl.zzpj.esportbetting.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.esportbetting.impl.UserToUserResponseConverter;
import pl.zzpj.esportbetting.interfaces.UserContextService;
import pl.zzpj.esportbetting.interfaces.UserService;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.request.ChangePasswordRequest;
import pl.zzpj.esportbetting.response.UserResponse;
import pl.zzpj.esportbetting.request.RegisterRequest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserRestController {

    private final UserService userService;
    private final UserContextService userContextService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserRestController(UserService userService, UserContextService userContextService, PasswordEncoder encoder) {
        this.userService = userService;
        this.userContextService = userContextService;
        this.encoder = encoder;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserToUserResponseConverter.convert(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users
                .stream()
                .map(UserToUserResponseConverter::convert)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody RegisterRequest registerRequest) {

        registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));

        User user = new User(registerRequest);

        userService.register(user);
        return ResponseEntity.ok("\"User successfully created!\"");
    }

    @PatchMapping(path = "/me/update", consumes = "application/json-patch+json", produces = "application/json")
    public ResponseEntity<User> updateUser(@RequestBody JsonPatch patch) throws JsonPatchException,
                                                                                JsonProcessingException {
        User loggedUser = userContextService.getAuthenticatedUser();
        User updatedUser = userService.applyPatchToCustomer(patch, loggedUser);
        updatedUser = userService.update(updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping(path = "/me/password", produces = "application/json")
    public ResponseEntity<User> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        User loggedUser = userContextService.getAuthenticatedUser();
        User updatedUser = userService.changePassword(loggedUser, changePasswordRequest.getOldPassword(),
                                                      changePasswordRequest.getNewPassword(), encoder);
        return ResponseEntity.ok(updatedUser);
    }

}
