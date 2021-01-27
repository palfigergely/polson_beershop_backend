package polson.webshop.users.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import polson.webshop.beers.models.entities.Beer;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  private Long id;
  private String username;
  private String brewery;
  private String email;
  private String city;
  private String country;
  private String logo;
  private List<Beer> sortiment;

}
