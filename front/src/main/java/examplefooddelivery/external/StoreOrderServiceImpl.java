package examplefooddelivery.external;

import org.springframework.stereotype.Service;

@Service
public class StoreOrderServiceImpl implements StoreOrderService {


    /**
     * Fallback
     */
    public StoreOrder getStoreOrder(Long id) {
        StoreOrder storeOrder = new StoreOrder();
        return storeOrder;
    }
}

