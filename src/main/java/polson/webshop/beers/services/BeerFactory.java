package polson.webshop.beers.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import polson.webshop.beers.models.dtos.BeerDTO;
import polson.webshop.beers.models.dtos.RegBeerDTO;
import polson.webshop.beers.models.entities.Beer;
import polson.webshop.users.models.entities.User;
import polson.webshop.users.services.UserService;

@Data
@Service
public class BeerFactory {

    private UserService userService;

    @Autowired
    public BeerFactory(@Lazy UserService userService) {
        this.userService = userService;
    }

    Beer createBeer(RegBeerDTO regBeerDTO, Long userId) {
        User user = userService.findUserById(userId);
        Beer beer = new Beer();
        beer.setBeerName(regBeerDTO.getBeerName());
        beer.setBrewery(user.getBrewery());
        beer.setUser(user);
        beer.setType(regBeerDTO.getType());
        beer.setIbu(regBeerDTO.getIbu());
        beer.setAbv(regBeerDTO.getAbv());
        beer.setEbc(regBeerDTO.getEbc());
        beer.setStock(regBeerDTO.getStock());
        beer.setRate(0F);
        return beer;
    }

    BeerDTO convertBeerToBeerDto(Beer beer) {
        BeerDTO beerDTO = new BeerDTO();
        beerDTO.setId(beer.getId());
        beerDTO.setBeerName(beer.getBeerName());
        beerDTO.setBrewery(beer.getBrewery());
        beerDTO.setType(beer.getType());
        beerDTO.setIbu(beer.getIbu());
        beerDTO.setAbv(beer.getAbv());
        beerDTO.setEbc(beer.getEbc());
        beerDTO.setStock(beer.getStock());
        beerDTO.setRate(beer.getRate());
        return beerDTO;
    }
}
