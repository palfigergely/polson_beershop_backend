package polson.webshop.users.repositories;

import org.springframework.data.repository.CrudRepository;
import polson.webshop.users.models.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
