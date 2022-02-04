package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface Image repository.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
