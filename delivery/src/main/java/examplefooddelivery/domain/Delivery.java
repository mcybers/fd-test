package examplefooddelivery.domain;

import examplefooddelivery.domain.Delivered;
import examplefooddelivery.domain.DeliveryStarted;
import examplefooddelivery.domain.DeliveryFailed;
import examplefooddelivery.DeliveryApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Delivery_table")
@Data

public class Delivery  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String address;
    
    
    
    
    
    private Long orderId;

    @PostPersist
    public void onPostPersist(){


        Delivered delivered = new Delivered(this);
        delivered.publishAfterCommit();



        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();



        DeliveryFailed deliveryFailed = new DeliveryFailed(this);
        deliveryFailed.publishAfterCommit();

    }
    @PrePersist
    public void onPrePersist(){
    }

    public static DeliveryRepository repository(){
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(DeliveryRepository.class);
        return deliveryRepository;
    }



    public void pick(){
        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }
    public void confirmDelivered(){
        Delivered delivered = new Delivered(this);
        delivered.publishAfterCommit();
    }
    public void accident(){
        DeliveryFailed delivered = new DeliveryFailed(this);
        delivered.publishAfterCommit();
    }

    public static void addDeliveryList(CookFinished cookFinished){

        Delivery delivery = new Delivery();
        delivery.setAddress(cookFinished.getAddress());
        delivery.setOrderId(cookFinished.getOrderId());
        repository().save(delivery);
        
    }


}
