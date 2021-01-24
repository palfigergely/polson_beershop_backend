package polson.webshop.beers.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import polson.webshop.beers.models.entities.BeerType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegBeerDTO {
    private String beerName;
    @Enumerated(EnumType.STRING)
    private BeerType type;
    private int ibu;
    private float abv;
    private float ebc;
    private Integer stock;
}
