package com.challenge.btgPactual.orderms.repository;

import com.challenge.btgPactual.orderms.controller.dto.OrderResponse;
import com.challenge.btgPactual.orderms.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

    Page<Order> findAllByCustomerId(Long customerId, PageRequest pageRequest);

}
