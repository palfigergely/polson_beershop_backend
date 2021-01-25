package polson.webshop.beers.services;

import org.springframework.stereotype.Service;
import polson.webshop.beers.models.dtos.BeerDTO;
import polson.webshop.beers.models.dtos.RegBeerDTO;
import polson.webshop.beers.models.entities.Beer;
import polson.webshop.security.JwtUserDetails;

import java.util.List;

@Service
public interface BeerService {
    BeerDTO saveBeer(JwtUserDetails userDetails, RegBeerDTO regBeerDTO);
    List<Beer> getBeersByBrewery(String brewery);
}
