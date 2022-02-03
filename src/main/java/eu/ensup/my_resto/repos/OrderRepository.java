package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Order;
import eu.ensup.my_resto.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    @Query(value = "SELECT SUM(price) FROM `order` where date_created like ?1%",nativeQuery = true)
    Double findSumPriceForMonth(String yearMonthDate);

}
