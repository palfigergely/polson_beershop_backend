package polson.webshop.beers.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import polson.webshop.users.models.entities.User;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "beers")
@FlywayDataSource
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String beerName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String brewery;
    @Enumerated(EnumType.STRING)
    private BeerType type;
    private int ibu;
    private float abv;
    private float ebc;
    private Integer stock;
    private float rate;

}
