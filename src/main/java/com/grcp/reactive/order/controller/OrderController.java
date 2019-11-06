package com.grcp.reactive.order.controller;

import com.grcp.reactive.order.model.OrderVo;
import com.grcp.reactive.order.service.OrderService;
import com.grcp.reactive.persistence.order.model.Order;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
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

    private URI buildOrderUri(Order order) {
        return UriComponentsBuilder.fromPath("/order/" + order.getId()).build().toUri();
    }
}
