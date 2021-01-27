package polson.webshop.users.services;

import polson.webshop.users.models.dtos.RegistrationDTO;
import polson.webshop.users.models.dtos.UserDTO;
import polson.webshop.users.models.entities.User;

public class UserFactory {

  public static User createUserWithId() {
    User user = User.builder()
        .id(999L)
        .username("User")
        .password("valami")
        .email("example@test.com")
        .brewery("testBrewery999")
        .city("City")
        .country("Country")
        .build();
    return user;
  }

  public static User createUser() {
    User user = User.builder()
        .username("User")
        .password("valami")
        .email("example@test.com")
        .brewery("Brewery")
        .city("City")
        .country("Country")
        .build();
    return user;
  }

  public static UserDTO createUserDTO(User user) {
    return UserDTO.builder()
     .id(user.getId())
     .username(user.getUsername())
     .email(user.getEmail())
     .brewery(user.getBrewery())
     .city(user.getCity())
     .country(user.getCountry())
     .build();
  }

  public static RegistrationDTO createRegistrationDTO() {
    RegistrationDTO registrationDTO = new RegistrationDTO();
    registrationDTO.setUsername("User");
    registrationDTO.setPassword("password");
    registrationDTO.setBrewery("Brewery");
    registrationDTO.setEmail("example@test.com");
    return registrationDTO;
  }
}