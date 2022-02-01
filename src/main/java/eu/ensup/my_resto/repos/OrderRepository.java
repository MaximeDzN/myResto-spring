package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
