package examplefooddelivery.domain;

import examplefooddelivery.domain.*;
import examplefooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class CookFinished extends AbstractEvent {

    private Long id;
    private String foodId;
    private Long orderId;
    private String status;

    public CookFinished(StoreOrder aggregate){
        super(aggregate);
    }
    public CookFinished(){
        super();
    }
}
