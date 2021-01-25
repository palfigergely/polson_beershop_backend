package polson.webshop.users.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import polson.webshop.beers.models.entities.Beer;
import polson.webshop.beers.services.BeerService;
import polson.webshop.exceptions.ApiException;
import polson.webshop.exceptions.AuthorisedUserNotFoundException;
import polson.webshop.exceptions.BrewerynameAlreadyTakenException;
import polson.webshop.exceptions.ParameterMissingException;
import polson.webshop.exceptions.WrongPasswordFormatException;
import polson.webshop.users.models.dtos.AuthenticationDTO;
import polson.webshop.users.models.dtos.LoginResponseDTO;
import polson.webshop.users.models.dtos.RegistrationDTO;
import polson.webshop.users.models.dtos.UserDTO;
import polson.webshop.users.models.entities.User;
import polson.webshop.users.repositories.UserRepository;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    @Value("${token.secretKey}")
    private String tokenSecretKey;
    private UserRepository userRepository;
    private BeerService beerService;

    public UserServiceImpl(UserRepository userRepository,
                           BeerService beerService) {
        this.userRepository = userRepository;
        this.beerService = beerService;
    }

    @Override
    public UserDTO registerUser(RegistrationDTO registrationDTO) throws ApiException {
        checkInputBody(registrationDTO);
        validateRegistrationData(registrationDTO);
        User user = userRepository.save(createUser(registrationDTO));
        return convertUserToUserDTO(user);
    }

    @Override
    public boolean checkInputBody(AuthenticationDTO authDTO) throws ParameterMissingException {
        boolean noUserName = authDTO.getUsername() == null || authDTO.getUsername().equals("");
        boolean noPassword = authDTO.getPassword() == null || authDTO.getPassword().equals("");
        if (noUserName && noPassword) {
            throw new ParameterMissingException("username", "password");
        }
        if (authDTO.getUsername() == null || authDTO.getUsername().equals("")) {
            throw new ParameterMissingException("username");
        }
        if (authDTO.getPassword() == null || authDTO.getPassword().equals("")) {
            throw new ParameterMissingException("password");
        }
        return true;
    }

    @Override
    public boolean validateRegistrationData(RegistrationDTO registrationDTO) throws ApiException {
        if (registrationDTO.getPassword().length() < 8) {
            throw new WrongPasswordFormatException();
        }
        if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
            throw new BrewerynameAlreadyTakenException();
        }
        return true;
    }

    @Override
    public User createUser(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setBrewery(registrationDTO.getBrewery());
        user.setPassword(encodePassword(registrationDTO.getPassword()));
        user.setEmail(registrationDTO.getEmail());
        user.setCity(registrationDTO.getCity());
        user.setCountry(registrationDTO.getCountry() == null ? "Hungary" : registrationDTO.getCountry());
        user.setLogo(registrationDTO.getLogo());
        user.setSortiment(null);
        return user;
    }

    @Override
    public UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        if (user == null) {
            return userDTO;
        }
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setBrewery(user.getBrewery());
        userDTO.setEmail(user.getEmail());
        userDTO.setCity(user.getCity());
        userDTO.setCountry(user.getCountry());
        userDTO.setLogo(user.getLogo());
        userDTO.setSortiment(user.getSortiment());
        return userDTO;
    }

    @Override
    public String encodePassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(10));
    }

    @Override
    public LoginResponseDTO login(AuthenticationDTO authDTO) throws RuntimeException {
        checkInputBody(authDTO);
        User user = findUser(authDTO.getUsername());
        return validateCredentials(user, authDTO.getPassword());
    }

    @Override
    public User findUser(String username) throws RuntimeException {
        return userRepository.findByUsername(username)
                .orElseThrow(AuthorisedUserNotFoundException::new);
    }

    @Override
    public User findUserById(Long userId) throws RuntimeException {
        return userRepository.findById(userId)
                .orElseThrow(AuthorisedUserNotFoundException::new);
    }

    @Override
    public LoginResponseDTO validateCredentials(User user, String password) throws AuthorisedUserNotFoundException {
        if (BCrypt.checkpw(password, user.getPassword())) {
            Map<String, String> payload = new HashMap<>();
            payload.put("userId", Long.toString(user.getId()));
            payload.put("userName", user.getUsername());

            Key key = Keys.hmacShaKeyFor(tokenSecretKey.getBytes());
            String jws = Jwts.builder().setClaims(payload).signWith(key).compact();
            return new LoginResponseDTO(jws);
        } else {
           throw new AuthorisedUserNotFoundException();
        }
    }

    @Override
    public UserDTO getUser(String userName) throws ApiException {
        User user = findUser(userName);
        user.setSortiment(beerService.getBeersByBrewery(user.getBrewery()));
        return convertUserToUserDTO(user);
    }
}
