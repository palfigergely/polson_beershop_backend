package polson.webshop.users.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class RegistrationDTO extends AuthenticationDTO {

  private String brewery;
  private String email;
  private String city;
  private String country;
  private String logo;

}