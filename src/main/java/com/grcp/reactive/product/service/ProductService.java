package com.grcp.reactive.product.service;

import com.grcp.reactive.persistence.product.model.Product;
import com.grcp.reactive.persistence.product.repository.ProductRepository;
import com.grcp.reactive.product.model.ProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository repository;

    public Mono<Product> createProduct(ProductVo productVo) {
        return buildProduct(productVo)
                .flatMap(repository::save);
    }

    public Mono<Product> retrieveProduct(String id) {
        return repository.findById(id);
    }

    public Flux<Product> findAllProducts() {
        return repository.findAll();
    }

    public Mono<Product> updateProduct(String id, ProductVo productVo) {
        return retrieveProduct(id)
                .flatMap(product -> updateProduct(product, productVo));
    }

    public Mono<Void> deleteProductById(String id) {
        return repository.deleteById(id);
    }

    private Mono<Product> buildProduct(ProductVo productVo) {
        Product product = Product.builder()
                .name(productVo.getName())
                .build();
        return Mono.just(product);
    }

    private Mono<Product> updateProduct(Product product, ProductVo productVo) {
        product.setName(productVo.getName());
        return repository.save(product);
    }
}
