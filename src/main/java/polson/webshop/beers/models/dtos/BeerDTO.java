package polson.webshop.beers.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {

    private int id;
    private String beerName;
    private String brewery;
    private String type;
    private int ibu;
    private float abv;
    private float ebc;
    private Integer stock;
    private float rate;

}
