package com.grcp.reactive.persistence.order.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
public class Order {

    @Id
    private String id;
    private List<ProductOrder> products;
}
