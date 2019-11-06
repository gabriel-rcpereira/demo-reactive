package com.grcp.reactive.persistence.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@Document
public class Product {

    @Id
    @HashIndexed
    private String id;
    private String name;
}
