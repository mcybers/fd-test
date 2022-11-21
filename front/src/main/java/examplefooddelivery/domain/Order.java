package examplefooddelivery.domain;

import examplefooddelivery.domain.OrderPlaced;
import examplefooddelivery.domain.OrderCanceled;
import examplefooddelivery.FrontApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Order_table")
@Data

public class Order  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String foodId;
    
    
    
    
    
    private String customerId;
    
    

    private String options;
    

    
    
    
    private String status;
    
    
    
    
    
    private String address;

    @PostPersist
    public void onPostPersist(){

        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();

    }

    @PostRemove
    public void onPostRemove(){


        OrderCanceled orderCanceled = new OrderCanceled(this);
        orderCanceled.publishAfterCommit();

    }
    @PrePersist
    public void onPrePersist(){
    }
    @PreRemove
    public void onPreRemove(){
    }

    public static OrderRepository repository(){
        OrderRepository orderRepository = FrontApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }




    public static void cancel(Rejected rejected){
        repository().findById(rejected.getOrderId()).ifPresent(order->{
            
            order.setStatus("주문 취소"); // do something
            repository().save(order);


         });
        
    }
    public static void updateStatus(Accepted accepted){

        repository().findById(accepted.getOrderId()).ifPresent(order->{
            
            order.setStatus("주문 수락"); // do something
            repository().save(order);


         });

        
    }
    public static void updateStatus(Rejected rejected){

        repository().findById(rejected.getOrderId()).ifPresent(order->{
            
            order.setStatus("주문 거절"); // do something
            repository().save(order);


         });

        
    }


}
