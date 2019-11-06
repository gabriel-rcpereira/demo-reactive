package com.grcp.reactive.order.controller;

import com.grcp.reactive.order.model.OrderVo;
import com.grcp.reactive.order.service.OrderService;
import com.grcp.reactive.persistence.order.model.Order;
import com.grcp.reactive.persistence.product.model.Product;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService service;

    @PostMapping("/order")
    public Mono<ResponseEntity<Void>> postCreateOrder(@RequestBody OrderVo orderVo){
        return service.createOrder(orderVo)
            .map(order -> ResponseEntity.created(buildOrderUri(order)).build());
    }

    @GetMapping("/product/{id}")
    public Mono<ResponseEntity<Order>> getRetrieveOrderById(@PathVariable("id") String id) {
        return service.retrieveOrderById(id)
                .map(order -> ResponseEntity.ok(order));
    }

    @GetMapping("/product")
    public Mono<ResponseEntity<List<Order>>> getFindAllOrders() {
        return service.findAllOrders()
                .collectList()
                .map(orders -> ResponseEntity.ok(orders));
    }

    private URI buildOrderUri(Order order) {
        return UriComponentsBuilder.fromPath("/order/" + order.getId()).build().toUri();
    }
}
