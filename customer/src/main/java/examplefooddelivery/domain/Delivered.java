package examplefooddelivery.domain;

import examplefooddelivery.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class Delivered extends AbstractEvent {

    private Long id;
    private String address;
    private Long orderId;
}
