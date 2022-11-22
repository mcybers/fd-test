package examplefooddelivery.domain;

import examplefooddelivery.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class OrderCanceled extends AbstractEvent {

    private Long id;
    private String foodId;
    private String customerId;
    private String options;
    private String status;
    private String address;
}
