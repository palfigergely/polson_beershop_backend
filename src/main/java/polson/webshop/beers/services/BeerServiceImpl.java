package polson.webshop.beers.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import polson.webshop.beers.models.dtos.BeerDTO;
import polson.webshop.beers.models.dtos.BeerListDTO;
import polson.webshop.beers.models.dtos.DelBeerDTO;
import polson.webshop.beers.models.dtos.RegBeerDTO;
import polson.webshop.beers.models.dtos.StockIncrDTO;
import polson.webshop.beers.models.entities.Beer;
import polson.webshop.beers.models.entities.BeerType;
import polson.webshop.beers.repositories.BeerRepository;
import polson.webshop.exceptions.ForbiddenRequestException;
import polson.webshop.exceptions.IdNotFoundException;
import polson.webshop.security.JwtUserDetails;

import java.util.ArrayList;
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

  @Override
  public BeerDTO getBeerById(Long id) {
    Beer beer = beerRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);
    return beerFactory.convertBeerToBeerDto(beer);
  }

  @Override
  public List<Beer> getAllBeers() {
    return (List<Beer>) beerRepository.findAll();
  }

  @Override
  public List<Beer> getBeersByType(String type) {
    BeerType beerType = BeerType.valueOf(type);
    return beerRepository.findBeersByType(beerType);
  }

  @Override
  public List<Beer> getBeersByBreweryAndByType(String brewery, String type) {
    BeerType beerType = BeerType.valueOf(type);
    return beerRepository.findBeersByBreweryAndType(brewery, beerType);
  }

  @Override
  public BeerListDTO getBeersSelectedByQuery(String brewery, String type) {
    List<Beer> beerList = new ArrayList<>();
    if (type.equals("") && brewery.equals("")) {
      beerList = getAllBeers();
    } else {
      if (!type.equals("") && !brewery.equals("")) {
        beerList = getBeersByBreweryAndByType(brewery, type);
      } else if (!type.equals("") && brewery.equals("")) {
        beerList = getBeersByType(type);
      } else if (type.equals("") && !brewery.equals("")) {
        beerList = getBeersByBrewery(brewery);
      }
    }
    return new BeerListDTO(beerList);
  }

  @Override
  public DelBeerDTO deleteBeer(JwtUserDetails userDetails, Long beerId) {
    Beer beer = beerRepository.findById(beerId)
                .orElseThrow(IdNotFoundException::new);
    if (!userDetails.getBrewery().equals(beer.getUser().getBrewery())) {
      throw new ForbiddenRequestException();
    }
    beerRepository.delete(beer);
    return new DelBeerDTO(beerId, beer.getBrewery());
  }

  @Override
  public BeerDTO increaseStock(JwtUserDetails userDetails, StockIncrDTO stockIncrDTO) {
    Beer beer = beerRepository.findById(stockIncrDTO.getBeerId())
            .orElseThrow(IdNotFoundException::new);
    if (!userDetails.getBrewery().equals(beer.getUser().getBrewery())) {
      throw new ForbiddenRequestException();
    }
    beer.setStock(beer.getStock() + stockIncrDTO.getStockIncreasedBy());
    return beerFactory.convertBeerToBeerDto(beerRepository.save(beer));
  }
}
