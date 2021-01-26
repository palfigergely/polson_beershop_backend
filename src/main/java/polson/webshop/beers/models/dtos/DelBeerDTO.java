package polson.webshop.beers.models.dtos;

import lombok.Data;

@Data
public class DelBeerDTO {

    private String status = "ok";
    private String message;

    public DelBeerDTO (Long beerId, String brewery) {
        this.message = "Id("+beerId+") beer successfully deleted from "+brewery+" brewery.";
    }
}
