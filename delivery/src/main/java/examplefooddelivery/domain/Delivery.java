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
    }
    public void confirmDelivered(){
    }

    public static void addDeliveryList(CookFinished cookFinished){

        /** Example 1:  new item 
        Delivery delivery = new Delivery();
        repository().save(delivery);

        */

        /** Example 2:  finding and process
        
        repository().findById(cookFinished.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);


         });
        */

        
    }


}
