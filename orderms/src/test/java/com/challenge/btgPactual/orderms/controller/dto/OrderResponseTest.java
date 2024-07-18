package com.challenge.btgPactual.orderms.controller.dto;

import com.challenge.btgPactual.orderms.factory.OrderEntityFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderResponseTest {

    @Nested
    class fromEntity {

        @Test
        void shouldMapCorrectlyAndBeOrderResponseClass() {

            var order = OrderEntityFactory.build();

            var response = OrderResponse.fromEntity(order);

            assertEquals(order.getOrderId(), response.orderId());
            assertEquals(order.getCustomerId(), response.customerId());
            assertEquals(order.getTotal(), response.total());
            assertEquals(response.getClass(), OrderResponse.class);
        }


    }

}
