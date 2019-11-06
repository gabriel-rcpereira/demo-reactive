package com.grcp.reactive.persistence.product.model;

import lombok.Builder;
import lombok.Getter;

import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Builder
@Document
public class Product {

    @Id
    private String id;
    private String name;

    @Transient
    public static String generateId(){
        return UUID.randomUUID().toString();
    }
}
