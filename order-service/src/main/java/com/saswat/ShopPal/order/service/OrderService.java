package com.saswat.ShopPal.order.service;

import com.saswat.ShopPal.order.client.InventoryClient;
import com.saswat.ShopPal.order.dto.OrderRequest;
import com.saswat.ShopPal.order.event.OrderPlacedEvent;
import com.saswat.ShopPal.order.model.Order;
import com.saswat.ShopPal.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
            order.setSkuCode(orderRequest.skuCode());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);

            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),orderRequest.userDetails().email());
            log.info("Start - Sending OrderPlacedEvent {} to Kafka topic order_placed",orderPlacedEvent);
            kafkaTemplate.send("order_placed",orderPlacedEvent);
            log.info("End - Sending OrderPlacedEvent {} to Kafka topic order_placed",orderPlacedEvent);

        } else {
            throw new RuntimeException("Product with SkuCode" + orderRequest.skuCode() + " is not in stock");
        }


    }
}
