package polson.webshop.beers.models.dtos;

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
public class BeerListDTO {
  private List<Beer> beers;
}
