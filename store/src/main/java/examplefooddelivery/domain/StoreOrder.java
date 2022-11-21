package examplefooddelivery.domain;

import examplefooddelivery.domain.CookFinished;
import examplefooddelivery.domain.Accepted;
import examplefooddelivery.domain.Rejected;
import examplefooddelivery.domain.CookStarted;
import examplefooddelivery.StoreApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="StoreOrder_table")
@Data

public class StoreOrder  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String foodId;
    
    
    
    
    
    private Long orderId;
    
    
    
    
    
    private String status;
    
    
    
    
    
    private String address;

    @PostPersist
    public void onPostPersist(){


        CookFinished cookFinished = new CookFinished(this);
        cookFinished.publishAfterCommit();



        Accepted accepted = new Accepted(this);
        accepted.publishAfterCommit();



        Rejected rejected = new Rejected(this);
        rejected.publishAfterCommit();



        CookStarted cookStarted = new CookStarted(this);
        cookStarted.publishAfterCommit();

    }

    public static StoreOrderRepository repository(){
        StoreOrderRepository storeOrderRepository = StoreApplication.applicationContext.getBean(StoreOrderRepository.class);
        return storeOrderRepository;
    }



    public void finishCook(){
        this.setStatus("요리 완료");
        CookFinished cookFinished = new CookFinished(this);
        
        cookFinished.publishAfterCommit();
        
    }
    public void accept(){
        this.setStatus("주문 승락");
        Accepted accepted = new Accepted(this);
        accepted.publishAfterCommit();
    }
    public void reject(){
        this.setStatus("주문 거절");
        Rejected rejected = new Rejected(this);
        rejected.publishAfterCommit();
    }
    public void startCook(){
        this.setStatus("요리 시작");
        CookStarted cookStarted = new CookStarted(this);
        cookStarted.publishAfterCommit();
    }

    public static void addOrderList(Paid paid){

        
        repository().findByOrderId(paid.getOrderId()).ifPresent(storeOrder->{
            
            storeOrder.setStatus("주문 완료");
            repository().save(storeOrder);

         });
        
    }
    public static void alramCancel(OrderCanceled orderCanceled){

      
        repository().findById(orderCanceled.getId()).ifPresent(storeOrder->{
            
            storeOrder.setStatus("주문취소");
            repository().save(storeOrder);
         });

        
    }
    public static void copyOrder(OrderPlaced orderPlaced){

        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setOrderId(orderPlaced.getId());
        storeOrder.setFoodId(orderPlaced.getFoodId());
        storeOrder.setAddress(orderPlaced.getAddress());
        storeOrder.setStatus("결제 중");
        repository().save(storeOrder);

        
    }


}
