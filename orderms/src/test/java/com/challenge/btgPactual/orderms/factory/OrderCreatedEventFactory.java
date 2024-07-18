package com.challenge.btgPactual.orderms.factory;

import com.challenge.btgPactual.orderms.listener.dto.OrderCreatedEvent;
import com.challenge.btgPactual.orderms.listener.dto.OrderItemEvent;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEventFactory {

    public static OrderCreatedEvent buildWithOneitem() {

        var orderItemEvent = new OrderItemEvent("Notebook", 50, BigDecimal.valueOf(900));

        var orderCreatedEvent = new OrderCreatedEvent(1L, 2L, List.of(orderItemEvent));

        return orderCreatedEvent;

    }

    public static OrderCreatedEvent buildWithTwoItems() {

        var orderItemEvent = new OrderItemEvent("Notebook", 50, BigDecimal.valueOf(900));
        var orderItemEvent2 = new OrderItemEvent("Notebook", 50, BigDecimal.valueOf(900));

        var orderCreatedEvent = new OrderCreatedEvent(1L, 2L, List.of(orderItemEvent, orderItemEvent2));

        return orderCreatedEvent;




    }

}
