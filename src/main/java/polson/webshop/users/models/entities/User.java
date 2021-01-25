package polson.webshop.users.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import polson.webshop.beers.models.entities.Beer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
@FlywayDataSource
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String brewery;
    @Column(columnDefinition = "char")
    private String password;
    private String email;
    private String city;
    private String country;
    private String logo;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Beer> sortiment;

}
