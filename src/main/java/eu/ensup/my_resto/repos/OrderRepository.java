package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Order;
import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.repos.Projections.MapProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * The interface Order repository.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all by user list.
     *
     * @param user the user
     * @return the list
     */
    List<Order> findAllByUser(User user);

    /**
     * Find sum price for month double.
     *
     * @param yearMonthDate the year month date
     * @return the double
     */
    @Query(value = "SELECT SUM(price) FROM `order` where date_created like ?1%",nativeQuery = true)
    Double findSumPriceForMonth(String yearMonthDate);

    /**
     * Find status nb list.
     *
     * @return the list
     */
    @Query(value = "SELECT status as name,count(*) as nb FROM `order` group by status", nativeQuery = true)
    List<MapProjection> findStatusNb();

}
