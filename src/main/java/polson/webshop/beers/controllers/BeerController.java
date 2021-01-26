package polson.webshop.beers.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import polson.webshop.beers.models.dtos.BeerDTO;
import polson.webshop.beers.models.dtos.BeerListDTO;
import polson.webshop.beers.models.dtos.DelBeerDTO;
import polson.webshop.beers.models.dtos.RegBeerDTO;
import polson.webshop.beers.models.dtos.StockIncrDTO;
import polson.webshop.beers.services.BeerService;
import polson.webshop.exceptions.ApiException;
import polson.webshop.security.JwtUserDetails;

@CrossOrigin
@RestController
@RequestMapping("/beer")
public class BeerController {

    private BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping
    public ResponseEntity<BeerDTO> createBeer(Authentication auth, @RequestBody RegBeerDTO regBeerDTO) throws ApiException {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        BeerDTO beerDTO = beerService.saveBeer(userDetails, regBeerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(beerDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeeryById(@PathVariable("id") Long id) throws ApiException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(beerService.getBeerById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<BeerListDTO> getBeersSelectedByQuery(
            @RequestParam(value = "brewery", defaultValue = "") String brewery,
            @RequestParam(value = "type", defaultValue = "") String type) throws ApiException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(beerService.getBeersSelectedByQuery(brewery, type));
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity<DelBeerDTO> deleteBeer(Authentication auth, @PathVariable Long beerId) throws ApiException {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        DelBeerDTO delBeerDTO = beerService.deleteBeer(userDetails, beerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(delBeerDTO);
    }

    @PutMapping
    public ResponseEntity<BeerDTO> increaseStock(Authentication auth, @RequestBody StockIncrDTO stockIncrDTO) throws ApiException {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(beerService.increaseStock(userDetails, stockIncrDTO));
    }
}
