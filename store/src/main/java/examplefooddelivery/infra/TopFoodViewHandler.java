package examplefooddelivery.infra;

import examplefooddelivery.domain.*;
import examplefooddelivery.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TopFoodViewHandler {


    @Autowired
    private TopFoodRepository topFoodRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1 (@Payload OrderPlaced orderPlaced) {
        try {

            if (!orderPlaced.validate()) return;

            // view 객체 생성
            TopFood topFood = new TopFood();
            // view 객체에 이벤트의 Value 를 set 함
            topFood.setFoodId(orderPlaced.getFoodId());
            topFood.setCount(0L);
            // view 레파지 토리에 save
            topFoodRepository.save(topFood);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_UPDATE_1(@Payload OrderPlaced orderPlaced) {
        try {
            if (!orderPlaced.validate()) return;
                // view 객체 조회

                topFoodRepository.findByFoodId(orderPlaced.getFoodId()).ifPresent(topFood -> {
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    topFood.setCount(topFood.getCount() + 1);
                    // view 레파지 토리에 save
                    topFoodRepository.save(topFood);
                });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

