package examplefooddelivery.external;

import org.springframework.stereotype.Service;

@Service
public class StoreOrderServiceImpl implements StoreOrderService {


    /**
     * Fallback
     */
    public StoreOrder getStoreOrder(Long id) {
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setOrderId(id);
        storeOrder.setFoodId("확인중");
        storeOrder.setStatus("대기");
        return storeOrder;
    }
}

