package polson.webshop.beers.services;

import org.springframework.stereotype.Service;
import polson.webshop.beers.models.dtos.BeerDTO;
import polson.webshop.beers.models.dtos.RegBeerDTO;
import polson.webshop.security.JwtUserDetails;

@Service
public interface BeerService {
    BeerDTO saveBeer(JwtUserDetails userDetails, RegBeerDTO regBeerDTO);
}
