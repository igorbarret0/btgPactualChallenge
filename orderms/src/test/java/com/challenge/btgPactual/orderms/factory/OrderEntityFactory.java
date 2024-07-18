package com.challenge.btgPactual.orderms.factory;

import com.challenge.btgPactual.orderms.entities.Order;
import com.challenge.btgPactual.orderms.entities.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public class OrderEntityFactory {

    public static Order build() {

        var orderId = 1L;
        var customerId = 2L;
        var total = BigDecimal.valueOf(100.0);

        var items = new OrderItem("Notebook", 1, BigDecimal.valueOf(200));

        var order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(customerId);
        order.setTotal(total);
        order.setItems(List.of(items));

        return order;
    }

    public static Page<Order> buildWithPage() {

        return new PageImpl<>(List.of(build()));
    }

}
