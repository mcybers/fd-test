package examplefooddelivery.infra;

import examplefooddelivery.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="favoriteFoods", path="favoriteFoods")
public interface FavoriteFoodRepository extends PagingAndSortingRepository<FavoriteFood, Long> {

    List<FavoriteFood> findByFoodId(String foodId);


    
}
