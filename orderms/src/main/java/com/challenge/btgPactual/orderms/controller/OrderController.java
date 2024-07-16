package com.challenge.btgPactual.orderms.controller;

import com.challenge.btgPactual.orderms.controller.dto.ApiResponse;
import com.challenge.btgPactual.orderms.controller.dto.OrderResponse;
import com.challenge.btgPactual.orderms.controller.dto.PaginationResponse;
import com.challenge.btgPactual.orderms.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable(name = "customerId") Long customerId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {


        var pageResponse = orderService.findAllCustomerById(customerId, PageRequest.of(page, pageSize));

        return ResponseEntity.ok(new ApiResponse<>(
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }

}
