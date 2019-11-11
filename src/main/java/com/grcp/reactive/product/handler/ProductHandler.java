package com.grcp.reactive.product.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.grcp.reactive.persistence.product.model.Product;
import com.grcp.reactive.product.exception.ProductErrorReason;
import com.grcp.reactive.product.exception.ProductException;
import java.net.URI;

import com.grcp.reactive.product.model.ProductVo;
import com.grcp.reactive.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ProductHandler {

    private final ProductService service;

    public Mono<ServerResponse> postCreateProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductVo.class)
                .flatMap(service::createProduct)
                .flatMap(product -> ServerResponse.created(buildProductIdUri(product))
                        .contentType(APPLICATION_JSON)
                        .build());
    }

    public Mono<ServerResponse> getRetrieveProductById(ServerRequest serverRequest) {
        return getIdFromPathParam(serverRequest)
                .flatMap(service::retrieveProduct)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(product));
    }

    public Mono<ServerResponse> getFindAllProducts(ServerRequest serverRequest) {
        return service.findAllProducts()
                .collectList()
                .flatMap(products -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(products));
    }

    public Mono<ServerResponse> putUpdateProduct(ServerRequest serverRequest) {
        return getIdFromPathParam(serverRequest)
                .flatMap(id -> serverRequest.bodyToMono(ProductVo.class)
                        .flatMap(productVo -> service.updateProduct(id, productVo))
                        .then(Mono.from(ServerResponse.noContent().build())));
    }

    public Mono<ServerResponse> deleteProductById(ServerRequest serverRequest) {
        return getIdFromPathParam(serverRequest)
                .flatMap(service::deleteProductById)
                .then(ServerResponse.noContent().build());
    }

    private Mono<String> getIdFromPathParam(ServerRequest serverRequest) {
        return Mono.just(serverRequest.pathVariable("id"))
                .switchIfEmpty(Mono.error(new ProductException(ProductErrorReason.INVALID_ID_PARAMETER)));
    }

    private URI buildProductIdUri(Product product) {
        return UriComponentsBuilder.fromPath("/product/" + product.getId()).build().toUri();
    }
}
