package examplefooddelivery.domain;

import examplefooddelivery.domain.*;
import examplefooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class CookStarted extends AbstractEvent {

    private Long id;
    private String foodId;
    private String preference;
    private Long orderId;
    private String status;

    public CookStarted(StoreOrder aggregate){
        super(aggregate);
    }
    public CookStarted(){
        super();
    }
}
