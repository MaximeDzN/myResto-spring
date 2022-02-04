package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Order item repository.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
