package polson.webshop.beers.services;

import org.springframework.stereotype.Service;
import polson.webshop.beers.models.dtos.BeerDTO;
import polson.webshop.beers.models.dtos.BeerListDTO;
import polson.webshop.beers.models.dtos.DelBeerDTO;
import polson.webshop.beers.models.dtos.RegBeerDTO;
import polson.webshop.beers.models.entities.Beer;
import polson.webshop.security.JwtUserDetails;

import java.util.List;

@Service
public interface BeerService {
    BeerDTO saveBeer(JwtUserDetails userDetails, RegBeerDTO regBeerDTO);
    List<Beer> getBeersByBrewery(String brewery);
    BeerDTO getBeerById(Long id);
    List<Beer> getAllBeers();
    List<Beer> getBeersByType(String type);
    List<Beer> getBeersByBreweryAndByType(String brewery, String type);
    BeerListDTO getBeersSelectedByQuery(String brewery, String type);
    DelBeerDTO deleteBeer(JwtUserDetails userdetails, Long beerId);
}
