package polson.webshop.beers.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockIncrDTO {
    private Long beerId;
    private Integer stockIncreasedBy;
}
