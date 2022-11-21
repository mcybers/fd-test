package examplefooddelivery.infra;

import examplefooddelivery.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel="topFoods", path="topFoods")
public interface TopFoodRepository extends PagingAndSortingRepository<TopFood, Long> {

    Optional<TopFood> findByFoodId(String foodId);


    
}
