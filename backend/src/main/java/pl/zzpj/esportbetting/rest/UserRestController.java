package pl.zzpj.esportbetting.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.esportbetting.impl.UserToUserResponseConverter;
import pl.zzpj.esportbetting.interfaces.UserContextService;
import pl.zzpj.esportbetting.interfaces.UserService;
import pl.zzpj.esportbetting.model.Statistics;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.response.UserResponse;
import pl.zzpj.esportbetting.request.RegisterRequest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserRestController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final UserContextService userContextServiceService;

    @Autowired
    public UserRestController(UserService userService, PasswordEncoder encoder, UserContextService userContextServiceService) {
        this.userService = userService;
        this.encoder = encoder;
        this.userContextServiceService = userContextServiceService;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") long id) {
        User user = userService.findById(id);
        Statistics statistics= userService.getUserStats(user);
        return ResponseEntity.ok(UserToUserResponseConverter.convert(user, statistics));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users
                .stream()
                .map( user -> UserToUserResponseConverter.convert(user, userService.getUserStats(user)))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody RegisterRequest registerRequest) {

        registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));

        User user = new User(registerRequest);

        userService.register(user);
        return ResponseEntity.ok("User successfully created");
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserResponse> getStats() {
        User loggedUser = userContextServiceService.getAuthenticatedUser();
        return findById(loggedUser.getId());
    }

}
