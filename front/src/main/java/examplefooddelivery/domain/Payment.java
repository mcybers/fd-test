package examplefooddelivery.domain;

import examplefooddelivery.domain.Paid;
import examplefooddelivery.FrontApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Payment_table")
@Data

public class Payment  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private Long orderId;

    @PostPersist
    public void onPostPersist(){


        Paid paid = new Paid(this);
        paid.publishAfterCommit();

    }

    public static PaymentRepository repository(){
        PaymentRepository paymentRepository = FrontApplication.applicationContext.getBean(PaymentRepository.class);
        return paymentRepository;
    }




    public static void cancelPayment(Rejected rejected){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        */

        /** Example 2:  finding and process
        
        repository().findById(rejected.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);


         });
        */

        
    }
    public static void cancelPayment(OrderCanceled orderCanceled){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderCanceled.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);


         });
        */

        
    }


}
