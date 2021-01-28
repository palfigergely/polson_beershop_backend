package polson.webshop.users.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import polson.webshop.beers.services.BeerService;
import polson.webshop.exceptions.AuthorisedUserNotFoundException;
import polson.webshop.exceptions.ParameterMissingException;
import polson.webshop.exceptions.UsernameAlreadyTakenException;
import polson.webshop.exceptions.WrongPasswordFormatException;
import polson.webshop.users.models.dtos.AuthenticationDTO;
import polson.webshop.users.models.dtos.LoginResponseDTO;
import polson.webshop.users.models.dtos.RegistrationDTO;
import polson.webshop.users.models.dtos.UserDTO;
import polson.webshop.users.models.entities.User;
import polson.webshop.users.repositories.UserRepository;

import java.util.Optional;

import static java.util.Objects.deepEquals;

public class UserServiceImplTest {
  private UserService userService;
  UserRepository mockUserRepository;
  BeerService mockBeerService;

  @Before
  public void setUp() {
    mockUserRepository = Mockito.mock(UserRepository.class);
    BeerService mockBeerService = Mockito.mock(BeerService.class);
    userService = new UserServiceImpl(mockUserRepository, mockBeerService);

    ReflectionTestUtils.setField(userService,
                "tokenSecretKey",
                "RfUjXnZr4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J");
  }

  @Test
  public void checkInputBodyShouldThrowExceptionWhenMissingPassword() {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO();
    authenticationDTO.setUsername("Geza");

    try {
      userService.checkInputBody(authenticationDTO);
    } catch (ParameterMissingException e) {
      Assert.assertEquals("Password is required.", e.getMessage());
      Assert.assertEquals(ParameterMissingException.HTTP_STATUS, e.getHttpStatus());
    }
  }

  @Test
  public void checkInputBodyShouldThrowExceptionWhenMissingCredentials() {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO();

    try {
      userService.checkInputBody(authenticationDTO);
    } catch (ParameterMissingException e) {
      Assert.assertEquals("Username and password are required.", e.getMessage());
      Assert.assertEquals(ParameterMissingException.HTTP_STATUS, e.getHttpStatus());
    }
  }

  @Test
  public void convertUserToUserDtoShouldConvertUserObjectToUserDtoObjectWithKingdom() {

    User user = UserFactory.createUserWithId();
    UserDTO expectedUserDTO = UserFactory.createUserDTO(user);

    UserDTO result = userService.convertUserToUserDTO(user);

    Assert.assertTrue(deepEquals(expectedUserDTO, result));
  }

  @Test
  public void convertUserToUserDtoShouldReturnWithEmptyUserDtoWhenNullIsGiven() {
    UserDTO result = userService.convertUserToUserDTO(null);

    Assert.assertNotNull(result);
    Assert.assertEquals(result.getId(), null);
  }

  @Test
  public void createUserObjShouldReturnUserWhenRegistrationIsGiven() {
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();
    User expected = UserFactory.createUser();

    User result = userService.createUser(registrationDTO);

    Assert.assertEquals(expected.getId(), result.getId());
    Assert.assertTrue(result.getPassword().length() == 60);
    Assert.assertEquals(expected.getEmail(), result.getEmail());
    Assert.assertEquals(expected.getBrewery(), result.getBrewery());
  }

  @Test
  public void validateRegistrationDataShouldThrowWrongPasswordFormatExceptionWhenPasswordIsShorterThanEight() {
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();
    registrationDTO.setPassword("1234567");

    try {
      userService.validateRegistrationData(registrationDTO);
    } catch (WrongPasswordFormatException e) {
      Assert.assertEquals("Password must be at least 8 characters long", e.getMessage());
    }
  }

  @Test
  public void validateCredentialsShouldThrowExceptionWhenPasswordNotMatch() {
    User user = UserFactory.createUserWithId();
    String requestPassword = "notCorrectPassword";

    try {
      userService.validateCredentials(user, requestPassword);
    } catch (AuthorisedUserNotFoundException e) {
      Assert.assertEquals("Username or password is incorrect", e.getMessage());
      Assert.assertEquals(AuthorisedUserNotFoundException.HTTP_STATUS, e.getHttpStatus());
    }
  }

  @Test
  public void validateRegistrationDataShouldThrowUsernameAlreadyTakenExceptionWhenUserAlreadyExist() {
    RegistrationDTO registrationDTO = new RegistrationDTO();
    registrationDTO.setUsername("Bela");
    registrationDTO.setPassword("password");
    User user = userService.createUser(registrationDTO);

    Mockito.doReturn(Optional.of(user)).when(mockUserRepository).findByUsername(registrationDTO.getUsername());

    try {
      userService.validateRegistrationData(registrationDTO);
    } catch (UsernameAlreadyTakenException e) {
      Assert.assertEquals("Username is already taken", e.getMessage());
    }
  }

  @Test
  public void validateCredentialsReturnsDtoWhenCredentialIsValid() {
    User user = UserFactory.createUserWithId();
    String requestPassword = "password";

    LoginResponseDTO result = userService.validateCredentials(user, requestPassword);

    Assert.assertEquals("ok", result.getStatus());
    Assert.assertNotNull(result.getToken());
  }

  @Test(expected = AuthorisedUserNotFoundException.class)
  public void loginShouldThrowExceptionWhenUserNotFoundInDB() {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO();
    authenticationDTO.setPassword("strangerPassword");
    authenticationDTO.setUsername("strangerUserName");
    String userName = authenticationDTO.getUsername();

    Mockito.doThrow(AuthorisedUserNotFoundException.class).when(mockUserRepository).findByUsername(userName);

    userService.login(authenticationDTO);
  }

  @Test(expected = AuthorisedUserNotFoundException.class)
  public void loginShouldThrowExceptionWhenPasswordNotMatchesToUsername() {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO();
    authenticationDTO.setPassword("notValidPassword");
    authenticationDTO.setUsername("username");
    String userName = authenticationDTO.getUsername();

    Mockito.doThrow(AuthorisedUserNotFoundException.class).when(mockUserRepository).findByUsername(userName);

    userService.login(authenticationDTO);
  }

  @Test
  public void loginShouldReturnsDtoWhenCredentialsAreValid() {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO("Bela", "password");
    User user = UserFactory.createUserWithId();

    Mockito.doReturn(Optional.of(user)).when(mockUserRepository).findByUsername(authenticationDTO.getUsername());
    LoginResponseDTO result = userService.login(authenticationDTO);

    Assert.assertEquals("ok", result.getStatus());
    Assert.assertNotNull(result.getToken());
  }

  @Test
  public void checkInputBodyShouldReturnTrueWhenProperLoginBody() {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO();
    authenticationDTO.setPassword("password");
    authenticationDTO.setUsername("username");

    boolean isValidLogin = userService.checkInputBody(authenticationDTO);

    Assert.assertTrue(isValidLogin);
  }

  @Test
  public void checkInputBodyShouldThrowExceptionWhenMissingUsername() {
    AuthenticationDTO authenticationDTO = new AuthenticationDTO();
    authenticationDTO.setPassword("password");

    try {
      userService.checkInputBody(authenticationDTO);
    } catch (ParameterMissingException e) {
      Assert.assertEquals("Username is required.", e.getMessage());
      Assert.assertEquals(ParameterMissingException.HTTP_STATUS, e.getHttpStatus());
    }
  }

  @Test(expected = UsernameAlreadyTakenException.class)
  public void registerUserShouldReturnException() {
    RegistrationDTO registrationDTO = UserFactory.createRegistrationDTO();
    User user = UserFactory.createUserWithId();

    Mockito.when(mockUserRepository.findByUsername(registrationDTO.getUsername())).thenReturn(Optional.of(user));

    userService.registerUser(registrationDTO);
  }
}