package com.challenge.btgPactual.orderms.service;

import com.challenge.btgPactual.orderms.controller.dto.OrderResponse;
import com.challenge.btgPactual.orderms.entities.Order;
import com.challenge.btgPactual.orderms.entities.OrderItem;
import com.challenge.btgPactual.orderms.listener.dto.OrderCreatedEvent;
import com.challenge.btgPactual.orderms.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(OrderCreatedEvent orderCreatedEvent) {

        var orderEntity = new Order();
        orderEntity.setOrderId(orderCreatedEvent.codigoPedido());
        orderEntity.setCustomerId(orderCreatedEvent.codigoCliente());
        orderEntity.setTotal(getTotal(orderCreatedEvent));

        orderEntity.setItems(orderCreatedEvent.itens()
                .stream()
                .map(item -> new OrderItem(item.produto(), item.quantidade(), item.preco()))
                .toList());

        orderRepository.save(orderEntity);
    }

    public Page<OrderResponse> findAllCustomerById(Long customerId, PageRequest pageRequest) {

        var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);

        return orders.map(OrderResponse::fromEntity);
    }

    private BigDecimal getTotal(OrderCreatedEvent orderCreatedEvent) {

        return orderCreatedEvent.itens().stream()
                .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
