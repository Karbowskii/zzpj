package pl.zzpj.esportbetting.rest;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.esportbetting.impl.UserToUserResponseConverter;
import pl.zzpj.esportbetting.interfaces.AuthenticationService;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.response.LoginResponse;
import pl.zzpj.esportbetting.response.UserResponse;
import pl.zzpj.esportbetting.request.LoginRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthRestController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Pair<User, String> data = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        UserResponse userResponse = UserToUserResponseConverter.convert(data.getKey());

        LoginResponse loginResponse = LoginResponse.builder()
                .token(data.getValue())
                .type("Bearer")
                .user(userResponse)
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}
