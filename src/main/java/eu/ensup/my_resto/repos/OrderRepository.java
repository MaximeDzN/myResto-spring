package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Order;
import eu.ensup.my_resto.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);
}
