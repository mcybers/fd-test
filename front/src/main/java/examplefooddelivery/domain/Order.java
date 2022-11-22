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
        if (orderPlaced.publish()) {
            
            examplefooddelivery.external.Payment payment = new examplefooddelivery.external.Payment();
            payment.setOrderId(this.id);
            // mappings goes here
            FrontApplication.applicationContext.getBean(examplefooddelivery.external.PaymentService.class)
                .pay(payment);
        }


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
        // Get request from StoreOrder
        examplefooddelivery.external.StoreOrder storeOrder = 
            FrontApplication.applicationContext.getBean(examplefooddelivery.external.StoreOrderService.class)
            .getStoreOrder(this.id);
    
        if (storeOrder.getStatus().equals("주문 완료") || storeOrder.getStatus().equals("주문 승락")) {
            status = "고객 주문 취소";
            repository().save(this);
        } else {
            throw new RuntimeException("주문 취소가 불가능");
        }

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
