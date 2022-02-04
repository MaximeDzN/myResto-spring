package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {
}
