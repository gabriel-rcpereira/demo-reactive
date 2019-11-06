package com.grcp.reactive.product.service;

import com.grcp.reactive.persistence.product.model.Product;
import com.grcp.reactive.persistence.product.repository.ProductRepository;
import com.grcp.reactive.product.model.ProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository repository;

    public Mono<Product> createProduct(ProductVo productVo) {
        return Mono.just(productVo)
                .map(vo -> buildProduct(vo))
                .flatMap(repository::save);
    }

    public Mono<Product> retrieveProduct(String id) {
        return repository.findById(id);
    }

    public Mono<Product> updateProduct(String id, ProductVo productVo) {
        return retrieveProduct(id)
                .flatMap(product -> updateProductEntity(product, productVo))
                .flatMap(repository::save);
    }

    public Mono<Void> deleteProductById(String id) {
        return repository.deleteById(id);
    }

    private Product buildProduct(ProductVo productVo) {
        return Product.builder()
                .id(Product.generateId())
                .name(productVo.getName())
                .build();
    }

    private Mono<Product> updateProductEntity(Product product, ProductVo productVo) {
        product.setName(productVo.getName());
        return Mono.just(product);
    }
}
