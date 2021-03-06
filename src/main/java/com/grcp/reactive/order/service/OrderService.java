package com.grcp.reactive.order.service;

import static com.grcp.reactive.persistence.order.model.Order.OrderStatus.CREATED;
import com.grcp.reactive.order.model.OrderVo;
import com.grcp.reactive.persistence.order.model.Order;
import com.grcp.reactive.persistence.order.model.ProductOrder;
import com.grcp.reactive.persistence.order.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;

    public Mono<Order> createOrder(OrderVo orderVo) {
        return buildOrder(orderVo)
                .flatMap(repository::save);
    }

    public Mono<Order> retrieveOrderById(String id) {
        return repository.findById(id);
    }

    public Flux<Order> findAllOrders() {
        return repository.findAll();
    }

    private Mono<Order> buildOrder(OrderVo orderVo) {
        return Mono.just(Order.builder()
                .status(CREATED)
                .products(buildProductsOrder(orderVo))
                .build());
    }

    private List<ProductOrder> buildProductsOrder(OrderVo orderVo) {
        return orderVo.getProducts().stream()
                .map(productOrderVo -> ProductOrder.builder()
                        .id(productOrderVo.getId())
                        .quantity(productOrderVo.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }
}
