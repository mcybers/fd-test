package examplefooddelivery.domain;

import examplefooddelivery.domain.*;
import examplefooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class DeliveryFailed extends AbstractEvent {

    private Long id;
    private String address;
    private Long orderId;

    public DeliveryFailed(Delivery aggregate){
        super(aggregate);
    }
    public DeliveryFailed(){
        super();
    }
}
