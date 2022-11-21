package examplefooddelivery.domain;

import examplefooddelivery.domain.*;
import examplefooddelivery.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class CookFinished extends AbstractEvent {

    private Long id;
    private String foodId;
    private Long orderId;
    private String status;
}


