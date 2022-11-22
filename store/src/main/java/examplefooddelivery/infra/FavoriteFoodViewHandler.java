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
public class FavoriteFoodViewHandler {


    @Autowired
    private FavoriteFoodRepository favoriteFoodRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1 (@Payload OrderPlaced orderPlaced) {
        try {

            if (!orderPlaced.validate()) return;

            if (!favoriteFoodRepository.findByFoodId(orderPlaced.getFoodId()).isPresent()) {
                // view 객체 생성
                FavoriteFood favoriteFood = new FavoriteFood();
                // view 객체에 이벤트의 Value 를 set 함
                favoriteFood.setFoodId(orderPlaced.getFoodId());
                favoriteFood.setCount(0L);

                // view 레파지 토리에 save
                favoriteFoodRepository.save(favoriteFood);
            }           

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenCookStarted_then_UPDATE_1(@Payload CookStarted cookStarted) {
        try {
            if (!cookStarted.validate()) return;
                // view 객체 조회
                
            favoriteFoodRepository.findByFoodId(cookStarted.getFoodId()).ifPresent(favoriteFood -> {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                favoriteFood.setCount(favoriteFood.getCount() + 1);
                // view 레파지 토리에 save
                favoriteFoodRepository.save(favoriteFood);
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

