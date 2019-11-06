package com.grcp.reactive.persistence.order.repository;

import com.grcp.reactive.persistence.order.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {

}
