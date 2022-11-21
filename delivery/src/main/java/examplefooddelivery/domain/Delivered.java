package examplefooddelivery.domain;

import examplefooddelivery.domain.*;
import examplefooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class Delivered extends AbstractEvent {

    private Long id;
    private String address;
    private Long orderId;

    public Delivered(Delivery aggregate){
        super(aggregate);
    }
    public Delivered(){
        super();
    }
}
