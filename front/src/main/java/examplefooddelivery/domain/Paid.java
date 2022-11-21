package examplefooddelivery.domain;

import examplefooddelivery.domain.*;
import examplefooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class Paid extends AbstractEvent {

    private Long id;
    private Long orderId;

    public Paid(Payment aggregate){
        super(aggregate);
    }
    public Paid(){
        super();
    }
}
