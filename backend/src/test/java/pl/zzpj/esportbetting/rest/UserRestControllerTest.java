package pl.zzpj.esportbetting.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.interfaces.UserService;
import pl.zzpj.esportbetting.model.Authority;
import pl.zzpj.esportbetting.model.Level;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.request.RegisterRequest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder encoder;
    
    @InjectMocks
    public UserRestController userRestController;

    public static MockMvc mockMvc;
    public static User user;
    public static User createdUser;
    public static List<User> users;

    @BeforeClass
    public static void init() {
        Set<Authority> authorityRoleAdmin = new HashSet<>();
        authorityRoleAdmin.add(Authority.builder().name(AuthorityEnum.ROLE_ADMIN).build());
        user = User.builder()
                .id(1)
                .username("username1")
                .password("hashedPassword1")
                .email("email@email")
                .firstName("Jan")
                .lastName("Kowalski")
                .level(Level.builder().id(1).build())
                .exp(1)
                .coins(100)
                .isActive(true)
                .authorities(authorityRoleAdmin)
                .build();
        createdUser = User.builder()
                .id(2)
                .username("username2")
                .password("hashedPassword2")
                .email("email2@email")
                .firstName("Tomek")
                .lastName("Jankowksi")
                .level(Level.builder().id(1).build())
                .exp(1)
                .coins(100)
                .isActive(true)
                .authorities(authorityRoleAdmin)
                .build();
        users = Arrays.asList(user, createdUser);
    }
    
    @Before
    public void setUp() {
        userRestController = new UserRestController(userService, encoder);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    public void whenFindByIdThenUserGiven() throws Exception {
        Mockito.when(userService.findById(user.getId())).thenReturn(user);

        mockMvc.perform(get("/users/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",
                        Matchers.is((Integer.parseInt(Long.toString(user.getId()))))))
                .andExpect(jsonPath("$.username", Matchers.is(user.getUsername())))
                .andExpect(jsonPath("$.email", Matchers.is(user.getEmail())))
                .andExpect(jsonPath("$.firstName", Matchers.is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(user.getLastName())))
                .andExpect(jsonPath("$.level.id",
                        Matchers.is(Integer.parseInt(Long.toString(user.getLevel().getId())))))
                .andExpect(jsonPath("$.exp", Matchers.is(user.getExp())))
                .andExpect(jsonPath("$.coins", Matchers.is(user.getCoins())))
                .andExpect(jsonPath("$.authorities.[0]",
                        Matchers.is((user.getAuthorities()
                                .stream()
                                .map(Authority::getName)
                                .findFirst().get()).name())))
                .andExpect(jsonPath("$.active" +
                        "", Matchers.is(user.getIsActive())));
    }

    @Test
    public void whenGetAllUserThenAllUsersGiven() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id",
                        Matchers.is((Integer.parseInt(Long.toString(users.get(0).getId()))))))
                .andExpect(jsonPath("$.[0].username", Matchers.is(users.get(0).getUsername())))
                .andExpect(jsonPath("$.[0].email", Matchers.is(users.get(0).getEmail())))
                .andExpect(jsonPath("$.[0].firstName", Matchers.is(users.get(0).getFirstName())))
                .andExpect(jsonPath("$.[0].lastName", Matchers.is(users.get(0).getLastName())))
                .andExpect(jsonPath("$.[0].level.id",
                        Matchers.is(Integer.parseInt(Long.toString(users.get(0).getLevel().getId())))))
                .andExpect(jsonPath("$.[0].exp", Matchers.is(users.get(0).getExp())))
                .andExpect(jsonPath("$.[0].coins", Matchers.is(users.get(0).getCoins())))
                .andExpect(jsonPath("$.[0].authorities.[0]",
                        Matchers.is((users.get(0).getAuthorities()
                                .stream()
                                .map(Authority::getName)
                                .findFirst().get()).name())))
                .andExpect(jsonPath("$.[0].active" +
                        "", Matchers.is(users.get(0).getIsActive())))

                .andExpect(jsonPath("$.[1].id",
                Matchers.is((Integer.parseInt(Long.toString(users.get(1).getId()))))))
                .andExpect(jsonPath("$.[1].username", Matchers.is(users.get(1).getUsername())))
                .andExpect(jsonPath("$.[1].email", Matchers.is(users.get(1).getEmail())))
                .andExpect(jsonPath("$.[1].firstName", Matchers.is(users.get(1).getFirstName())))
                .andExpect(jsonPath("$.[1].lastName", Matchers.is(users.get(1).getLastName())))
                .andExpect(jsonPath("$.[1].level.id",
                        Matchers.is(Integer.parseInt(Long.toString(users.get(1).getLevel().getId())))))
                .andExpect(jsonPath("$.[1].exp", Matchers.is(users.get(1).getExp())))
                .andExpect(jsonPath("$.[1].coins", Matchers.is(users.get(1).getCoins())))
                .andExpect(jsonPath("$.[1].authorities.[0]",
                        Matchers.is((users.get(1).getAuthorities()
                                .stream()
                                .map(Authority::getName)
                                .findFirst().get()).name())))
                .andExpect(jsonPath("$.[1].active" +
                        "", Matchers.is(users.get(1).getIsActive())));
    }

   /* @Test
    public void whenCreateUserThenUserCreated() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(createdUser.getUsername())
                .password("password1BeforeHashing")
                .firstName(createdUser.getFirstName())
                .lastName(createdUser.getLastName())
                .email(createdUser.getEmail())
                .build();
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String registerRequestJson = objectWriter.writeValueAsString(registerRequest);
        Mockito.when(encoder.encode(registerRequest.getPassword())).thenReturn(createdUser.getPassword());
        mockMvc.perform(post("/users/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestJson))
                .andExpect(status().isOk());
    }*/
}
