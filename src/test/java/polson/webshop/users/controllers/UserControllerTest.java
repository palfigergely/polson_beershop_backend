package polson.webshop.users.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import polson.webshop.users.models.dtos.AuthenticationDTO;
import polson.webshop.users.models.dtos.LoginResponseDTO;
import polson.webshop.users.models.dtos.RegistrationDTO;
import polson.webshop.users.models.dtos.UserDTO;
import polson.webshop.users.services.UserFactory;
import polson.webshop.users.services.UserService;

import java.util.Objects;

public class UserControllerTest {

  private UserController userController;
  private UserService mockUserService;

  @Before
  public void setUp() throws Exception {
    mockUserService = Mockito.mock(UserService.class);
    userController = new UserController(mockUserService);
  }

  @Test
  public void postLoginReturns200andLoginResponseDTO() {
    AuthenticationDTO input = new AuthenticationDTO("username", "password");
    LoginResponseDTO loginResponseDTO = new LoginResponseDTO("test.token.string");

    Mockito.doReturn(loginResponseDTO).when(mockUserService).login(input);
    ResponseEntity<LoginResponseDTO> output = userController.login(input);

    Assert.assertEquals(HttpStatus.OK, output.getStatusCode());
    Assert.assertNotNull(Objects.requireNonNull(output.getBody()).getToken());
  }

  @Test
  public void registerUserReturnsCreatedAndUserDTO() {
    UserDTO userDTO = UserFactory.createUserDTO(UserFactory.createUserWithId());
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();

    Mockito.doReturn(userDTO).when(mockUserService).registerUser(registrationDTO);

    ResponseEntity<?> response = userController.registerUser(registrationDTO);

    Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Assert.assertEquals(userDTO.getId(), ((UserDTO)response.getBody()).getId());
  }

}