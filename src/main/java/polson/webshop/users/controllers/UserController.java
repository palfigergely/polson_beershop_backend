package polson.webshop.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import polson.webshop.exceptions.ApiException;
import polson.webshop.security.JwtUserDetails;
import polson.webshop.users.models.dtos.AuthenticationDTO;
import polson.webshop.users.models.dtos.LoginResponseDTO;
import polson.webshop.users.models.dtos.RegistrationDTO;
import polson.webshop.users.models.dtos.UserDTO;
import polson.webshop.users.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registration)
        throws RuntimeException {
        UserDTO registeredUser = userService.registerUser(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO body)
        throws RuntimeException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(body));
    }

    @GetMapping("")
    public ResponseEntity<?> getUserDetials(Authentication auth) throws ApiException {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(userDetails.getUsername()));
    }
}