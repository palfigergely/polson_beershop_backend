package polson.webshop.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import polson.webshop.security.config.TestNoSecurityConfig;
import polson.webshop.users.models.dtos.AuthenticationDTO;
import polson.webshop.users.models.dtos.RegistrationDTO;
import polson.webshop.users.models.dtos.UserDTO;
import polson.webshop.users.services.UserFactory;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestNoSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerIT {

  @Autowired
  private UserController userController;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void loginUserReturns200AndWithDTO() throws Exception {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO("User", "password");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(authenticationDTO)))
      .andExpect(status().is(HttpStatus.OK.value()))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.status", is("ok")))
      .andExpect(jsonPath("$.token").isString());
  }

  @Test
  public void loginUserReturns400WhenNoUsername() throws Exception {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO("", "password");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(authenticationDTO)))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.status", is("error")))
      .andExpect(jsonPath("$.message", is("Username is required.")));
  }

  @Test
  public void loginUserReturns400WhenNoPassword() throws Exception {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO("User", "");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(authenticationDTO)))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.status", is("error")))
      .andExpect(jsonPath("$.message", is("Password is required.")));
  }

  @Test
  public void loginUserReturns400WhenNoUsernameNorPassword() throws Exception {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO("", "");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(authenticationDTO)))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.status", is("error")))
      .andExpect(jsonPath("$.message", is("Username and password are required.")));
  }

  @Test
  public void loginUserReturns404WhenNoUserWithUsernameInTheDB() throws Exception {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO("veryUnusualUserName", "password");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(authenticationDTO)))
      .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.status", is("error")))
      .andExpect(jsonPath("$.message", is("No such user.")));
  }

  @Test
  public void loginUserReturns401WhenPasswordNoMatchInTheDB() throws Exception {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO("User", "notMatchingPassword");

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(authenticationDTO)))
      .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.status", is("error")))
      .andExpect(jsonPath("$.message", is("Username or password is incorrect.")));
  }

  @Test
  public void registerUserReturnsCreatedAndUserDTO() throws Exception {
    UserDTO userDTO = UserFactory.createUserDTO(UserFactory.createUserWithId());
    String username = "I am a random user";
    userDTO.setUsername(username);
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();
    registrationDTO.setUsername(username);

    mockMvc.perform(post("/register")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(registrationDTO)))
      .andExpect(status().is(HttpStatus.CREATED.value()))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.username", is(userDTO.getUsername())));
  }

  @Test
  public void registerUserShouldThrowParameterMissingExceptionWhenUsernameMissing() throws Exception {
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();
    registrationDTO.setUsername("");

    mockMvc.perform(post("/register")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(registrationDTO)))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
      .andExpect(jsonPath("$.message", is("Username is required.")));
  }

  @Test
  public void registerUserShouldThrowParameterMissingExceptionWhenUsernameAndPasswordMissing() throws Exception {
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();
    registrationDTO.setUsername("");
    registrationDTO.setPassword("");

    mockMvc.perform(post("/register")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(registrationDTO)))
      .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
      .andExpect(jsonPath("$.message", is("Username and password are required.")));
  }

  @Test
  public void registerUserShouldThrowUsernameAlreadyTakenException() throws Exception {
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();

    mockMvc.perform(post("/register")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(registrationDTO)))
      .andExpect(status().is(HttpStatus.CONFLICT.value()))
      .andExpect(jsonPath("$.message", is("Username is already taken.")));
  }

  @Test
  public void registerUserShouldThrowWrongPasswordFormatException() throws Exception {
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();
    registrationDTO.setPassword("short");

    mockMvc.perform(post("/register")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(registrationDTO)))
      .andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()))
      .andExpect(jsonPath("$.message", is("Password must be at least 8 characters.")));
  }
}
