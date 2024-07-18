package com.challenge.btgPactual.orderms.service;

import com.challenge.btgPactual.orderms.controller.dto.OrderResponse;
import com.challenge.btgPactual.orderms.entities.Order;
import com.challenge.btgPactual.orderms.factory.OrderCreatedEventFactory;
import com.challenge.btgPactual.orderms.factory.OrderEntityFactory;
import com.challenge.btgPactual.orderms.factory.OrderResponseFactory;
import com.challenge.btgPactual.orderms.repository.OrderRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;

    @Nested
    class SaveOrder {

        @Test
        void shouldCallRepository() {

            var event = OrderCreatedEventFactory.buildWithOneitem();

            orderService.saveOrder(event);

            verify(orderRepository, times(1)).save(any());



        }

        @Test
        void shouldMapEventToEntityWithSuccess() {

            var event = OrderCreatedEventFactory.buildWithOneitem();

            orderService.saveOrder(event);
            verify(orderRepository, times(1)).save(orderArgumentCaptor.capture());

            var entity = orderArgumentCaptor.getValue();

            assertEquals(event.codigoPedido(), entity.getOrderId());
            assertEquals(event.codigoCliente(), entity.getCustomerId());
            assertNotNull(entity.getTotal());
            assertEquals(event.itens().getFirst().produto(), entity.getItems().getFirst().getProduct());
            assertEquals(event.itens().getFirst().quantidade(), entity.getItems().getFirst().getQuantity());
            assertEquals(event.itens().getFirst().preco(), entity.getItems().getFirst().getPrice());

        }

        @Test
        void shouldCallGetTotalWithSuccess() {


            var event = OrderCreatedEventFactory.buildWithTwoItems();
            var totalItem1 = event.itens().getFirst().preco().multiply(BigDecimal.valueOf(event.itens().getFirst().quantidade()));
            var totalItem2 = event.itens().getLast().preco().multiply(BigDecimal.valueOf(event.itens().getLast().quantidade()));
            var orderTotal = totalItem1.add(totalItem2);

            orderService.saveOrder(event);
            verify(orderRepository, times(1)).save(orderArgumentCaptor.capture());

            var entity = orderArgumentCaptor.getValue();

            assertNotNull(entity.getTotal());
            assertEquals(entity.getTotal(), orderTotal);

        }

    }

    @Nested
    class FindAllCustomerById {

        @Test
        void shouldCallRepository() {

            var customerId = 1L;
            var pageRequests = PageRequest.of(0, 10);

            doReturn(OrderEntityFactory.buildWithPage())
                   .when(orderRepository).findAllByCustomerId(customerId, pageRequests);

            orderService.findAllCustomerById(customerId, pageRequests);

            verify(orderRepository, times(1)).findAllByCustomerId(eq(customerId), eq(pageRequests));

        }

        @Test
        void shouldMapResponseToEntity() {

            var customerId = 1L;
            var pageRequests = PageRequest.of(0, 10);
            var page = OrderEntityFactory.buildWithPage();

            doReturn(page)
                    .when(orderRepository).findAllByCustomerId(anyLong(), any());

            var response = orderService.findAllCustomerById(customerId, pageRequests);

            assertEquals(page.getTotalPages(), response.getTotalPages());
            assertEquals(page.getTotalElements(), response.getTotalElements());
            assertEquals(page.getSize(), response.getSize());
            assertEquals(page.getNumber(), response.getNumber());

            assertEquals(page.getContent().getFirst().getOrderId(), response.getContent().getFirst().orderId());
            assertEquals(page.getContent().getFirst().getCustomerId(), response.getContent().getFirst().customerId());

        }

    }

}
