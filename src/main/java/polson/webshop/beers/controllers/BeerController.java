package polson.webshop.beers.controllers;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import polson.webshop.beers.services.BeerService;
import polson.webshop.security.JwtUserDetails;

@CrossOrigin
@RestController
@RequestMapping("/beer")
public class BeerController {

    private BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping("")
    public ResponseEntity<?> getBeers(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        BeerDTO beerDTO = beerService.saveBeer();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(beer);
    }

    /*@GetMapping("/all")
    public ResponseEntity<?> getBeers() {

    }*/

    /*@GetMapping("/{id}")
    public ResponseEntity<?> getBeers() {

    }*/

    /*@PutMapping("/{id}")
    public ResponseEntity<?> getBeers() {

    }*/

    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> getBeers() {

    }*/
}