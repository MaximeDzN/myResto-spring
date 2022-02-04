package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Order;
import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.repos.Projections.MapProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    @Query(value = "SELECT SUM(price) FROM `order` where date_created like ?1%",nativeQuery = true)
    Double findSumPriceForMonth(String yearMonthDate);

    @Query(value = "SELECT status as name,count(*) as nb FROM `order` group by status", nativeQuery = true)
    List<MapProjection> findStatusNb();

}
