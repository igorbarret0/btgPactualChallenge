package com.challenge.btgPactual.orderms.controller;

import com.challenge.btgPactual.orderms.factory.OrderResponseFactory;
import com.challenge.btgPactual.orderms.service.OrderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {


    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    @Captor
    ArgumentCaptor<Long> customerIdCaptor;

    @Captor
    ArgumentCaptor<PageRequest> pageRequestCaptor;

    // ARRANGE
    // ACT
    // ASSERT

    @Nested
    class ListOrders {

        @Test
        void shouldReturnHttpOk() {

            var customerId = 1L;
            var page = 0;
            var pageSize = 10;

            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService).findAllCustomerById(anyLong(), any());

            var response = orderController.listOrders(customerId, page, pageSize);

            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        }

        @Test
        void shouldPassCorrectParametersToService() {

            var customerId = 1L;
            var page = 0;
            var pageSize = 10;

            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService).findAllCustomerById(customerIdCaptor.capture(), pageRequestCaptor.capture());

            var response = orderController.listOrders(customerId, page, pageSize);

            assertEquals(1, customerIdCaptor.getAllValues().size());
            assertEquals(customerId, customerIdCaptor.getValue());
            assertEquals(page, pageRequestCaptor.getValue().getPageNumber());
            assertEquals(pageSize, pageRequestCaptor.getValue().getPageSize());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {

            var customerId = 1L;
            var page = 0;
            var pageSize = 10;
            var pagination = OrderResponseFactory.buildWithOneItem();

            doReturn(pagination)
                    .when(orderService).findAllCustomerById(anyLong(),
                            any());

            var response = orderController.listOrders(customerId, page, pageSize);

            assertNotNull(response);
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().data());
            assertNotNull(response.getBody().paginationResponse());

            assertEquals(pagination.getTotalElements(), response.getBody().paginationResponse().totalElements());
            assertEquals(pagination.getTotalPages(), response.getBody().paginationResponse().totalPages());

            assertEquals(pagination.getContent(), response.getBody().data());

        }

    }

}
