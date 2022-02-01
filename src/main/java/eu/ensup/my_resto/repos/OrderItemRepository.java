package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
