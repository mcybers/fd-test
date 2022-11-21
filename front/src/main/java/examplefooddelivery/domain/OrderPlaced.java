package examplefooddelivery.domain;

import examplefooddelivery.domain.*;
import examplefooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long id;
    private String foodId;
    private String customerId;
    private String preference;
    private String options;
    private String address;
    private String status;

    public OrderPlaced(Order aggregate){
        super(aggregate);
    }
    public OrderPlaced(){
        super();
    }
}
