package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Item;
import eu.ensup.my_resto.repos.Projections.MapProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select category as name, count(*) as nb from item group by category",nativeQuery = true)
    List<MapProjection> findNbItemCategory();

}
