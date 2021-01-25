package polson.webshop.beers.repositories;

import org.springframework.data.repository.CrudRepository;
import polson.webshop.beers.models.entities.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerRepository extends CrudRepository<Beer, Long> {
    Optional<Beer> findBeerByBeerName(String beerName);
    List<Beer> findBeersByBrewery(String brewery);
}
