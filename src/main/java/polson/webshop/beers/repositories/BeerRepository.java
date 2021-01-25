package polson.webshop.beers.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import polson.webshop.beers.models.entities.Beer;
import polson.webshop.beers.models.entities.BeerType;

import java.util.List;
import java.util.Optional;

public interface BeerRepository extends CrudRepository<Beer, Long> {
    Optional<Beer> findBeerByBeerName(String beerName);
    List<Beer> findBeersByBrewery(String brewery);
    List<Beer> findBeersByType(BeerType type);
    List<Beer> findBeersByBreweryAndType(String brewery, BeerType type);
}
