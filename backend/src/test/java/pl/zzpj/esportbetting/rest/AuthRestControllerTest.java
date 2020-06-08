package pl.zzpj.esportbetting.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.tuple.Pair;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.interfaces.AuthenticationService;
import pl.zzpj.esportbetting.model.Authority;
import pl.zzpj.esportbetting.model.Level;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.request.LoginRequest;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class AuthRestControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthRestController authController;


    private static User activeUser;
    private static Level firstLevel;
    private static String token;
    private static int id;
    private static LoginRequest loginRequest;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeClass
    public static void init() {
        id = 1;
        firstLevel = Level.builder().id(1).build();
        loginRequest = LoginRequest.builder()
                .username("username1")
                .password("12345678")
                .build();
        Set<Authority> authorityRoleAdmin = new HashSet<>();
        authorityRoleAdmin.add(Authority.builder().name(AuthorityEnum.ROLE_ADMIN).build());
        activeUser = User.builder()
                .id(id)
                .username("username1")
                .password("pass1")
                .email("email@email")
                .firstName("Jan")
                .lastName("Kowalski")
                .level(firstLevel)
                .exp(1)
                .coins(100)
                .isActive(true)
                .authorities(authorityRoleAdmin)
                .build();
    }

    @Before
    public void setUp() {
        authController = new AuthRestController(authenticationService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void whenAuthenticateUserThenUserAuthenticated() throws Exception {
        Mockito.when(authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword()))
            .thenReturn(Pair.of(activeUser, token));

        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(loginRequest);

        mockMvc.perform(post("/auth/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type", Matchers.is("Bearer")))
            .andExpect(jsonPath("$.token", Matchers.is(token)))
            .andExpect(jsonPath("$.user.id",
                    Matchers.is((Integer.parseInt(Long.toString(activeUser.getId()))))))
            .andExpect(jsonPath("$.user.username", Matchers.is(activeUser.getUsername())))
            .andExpect(jsonPath("$.user.email", Matchers.is(activeUser.getEmail())))
            .andExpect(jsonPath("$.user.firstName", Matchers.is(activeUser.getFirstName())))
            .andExpect(jsonPath("$.user.lastName", Matchers.is(activeUser.getLastName())))
            .andExpect(jsonPath("$.user.levelId",
                    Matchers.is(Integer.parseInt(Long.toString(activeUser.getLevel().getId())))))
            .andExpect(jsonPath("$.user.exp", Matchers.is(activeUser.getExp())))
            .andExpect(jsonPath("$.user.coins", Matchers.is(activeUser.getCoins())))
            .andExpect(jsonPath("$.user.authorities.[0]",
                    Matchers.is((activeUser.getAuthorities()
                            .stream()
                            .map(Authority::getName)
                            .findFirst().get()).name())))
            .andExpect(jsonPath("$.user.active" +
                    "", Matchers.is(activeUser.getIsActive())));

        Mockito.verify(authenticationService, Mockito.times(1)).authenticate(
            ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }
}
