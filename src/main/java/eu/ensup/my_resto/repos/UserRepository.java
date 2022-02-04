package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(String username);

}
