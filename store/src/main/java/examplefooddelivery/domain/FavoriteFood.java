package examplefooddelivery.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;

@Entity
@Table(name="FavoriteFood_table")
@Data
public class FavoriteFood {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String foodId;
        private Long count;


}