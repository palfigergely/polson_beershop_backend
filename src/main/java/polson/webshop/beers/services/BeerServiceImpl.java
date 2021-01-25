package polson.webshop.beers.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import polson.webshop.beers.models.dtos.BeerDTO;
import polson.webshop.beers.models.dtos.RegBeerDTO;
import polson.webshop.beers.models.entities.Beer;
import polson.webshop.beers.repositories.BeerRepository;
import polson.webshop.security.JwtUserDetails;

import java.util.List;

@Service
@AllArgsConstructor
public class BeerServiceImpl implements BeerService {

    public BeerRepository beerRepository;
    public BeerFactory beerFactory;

    @Override
    public BeerDTO saveBeer(JwtUserDetails userDetails, RegBeerDTO regBeerDTO) {
        Beer beer = beerRepository.save(beerFactory.createBeer(regBeerDTO, userDetails.getUserId()));
        return beerFactory.convertBeerToBeerDto(beer);
    }

    @Override
    public List<Beer> getBeersByBrewery(String brewery) {
        return beerRepository.findBeersByBrewery(brewery);
    }

}
