package com.grcp.reactive.persistence.order.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOrder {

    private String id;
    private long quantity;
}
