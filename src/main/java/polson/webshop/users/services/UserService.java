package polson.webshop.users.services;

import org.springframework.stereotype.Service;
import polson.webshop.exceptions.ApiException;
import polson.webshop.exceptions.AuthorisedUserNotFoundException;
import polson.webshop.exceptions.ParameterMissingException;
import polson.webshop.users.models.dtos.AuthenticationDTO;
import polson.webshop.users.models.dtos.LoginResponseDTO;
import polson.webshop.users.models.dtos.RegistrationDTO;
import polson.webshop.users.models.dtos.UserDTO;
import polson.webshop.users.models.entities.User;

@Service
public interface UserService {

  UserDTO registerUser(RegistrationDTO registrationDTO) throws ApiException;

  boolean checkInputBody(AuthenticationDTO authDTO) throws ParameterMissingException;

  boolean validateRegistrationData(RegistrationDTO registrationDTO) throws ApiException;

  User createUser(RegistrationDTO registrationDTO);

  UserDTO convertUserToUserDTO(User user);

  String encodePassword(String plaintext);

  LoginResponseDTO login(AuthenticationDTO authDTO) throws RuntimeException;

  User findUser(String username) throws RuntimeException;

  User findUserById(Long userId) throws RuntimeException;

  LoginResponseDTO validateCredentials(User user, String password) throws AuthorisedUserNotFoundException;

  UserDTO getUser(String userName) throws ApiException;
}
