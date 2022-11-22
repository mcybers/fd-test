package examplefooddelivery.external;

import lombok.Data;
import java.util.Date;
@Data
public class StoreOrder {

    private Long id;
    private String foodId;
    private Long orderId;
    private String status;
    private String address;
}


