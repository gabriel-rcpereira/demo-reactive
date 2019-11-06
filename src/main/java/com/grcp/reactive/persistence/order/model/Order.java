package com.grcp.reactive.persistence.order.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
public class Order {

    @Id
    @HashIndexed
    private String id;
    private OrderStatus status;
    private List<ProductOrder> products;

    public enum OrderStatus {
        CREATED;
    }
}
