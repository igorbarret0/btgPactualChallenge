package com.challenge.btgPactual.orderms.listener;

import com.challenge.btgPactual.orderms.factory.OrderCreatedEventFactory;
import com.challenge.btgPactual.orderms.service.OrderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.MessageBuilder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderCreatedListenerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderCreatedListener orderCreatedListener;

    @Nested
    class Listener {

        @Test
        void shouldCallServiceWithCorrectParameters() {

            var event = OrderCreatedEventFactory.buildWithOneitem();
            var message = MessageBuilder.withPayload(event).build();

            orderCreatedListener.listener(message);

             verify(orderService, times(1)).saveOrder(eq(message.getPayload()));
        }

    }

}
