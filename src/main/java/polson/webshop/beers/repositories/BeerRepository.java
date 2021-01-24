package polson.webshop.beers.repositories;

import org.springframework.data.repository.CrudRepository;
import polson.webshop.beers.models.entities.Beer;

import java.util.Optional;

public interface BeerRepository extends CrudRepository<Beer, Long> {
    Optional<polson.webshop.beers.models.entities.Beer> findBeerByBeerName(String beerName);
}
