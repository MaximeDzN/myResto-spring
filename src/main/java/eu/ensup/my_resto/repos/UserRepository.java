package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
