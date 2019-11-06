package com.grcp.reactive.persistence.product.repository;

import com.grcp.reactive.persistence.product.model.Product;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}
