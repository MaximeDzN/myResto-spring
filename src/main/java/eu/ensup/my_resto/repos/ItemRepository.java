package eu.ensup.my_resto.repos;

import eu.ensup.my_resto.domain.Item;
import eu.ensup.my_resto.repos.Projections.MapProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * The interface Item repository.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Find nb item category list.
     *
     * @return the list
     */
    @Query(value = "select category as name, count(*) as nb from item group by category",nativeQuery = true)
    List<MapProjection> findNbItemCategory();

}
