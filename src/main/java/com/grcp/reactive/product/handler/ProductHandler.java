package com.grcp.reactive.product.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.grcp.reactive.product.model.ProductVo;
import com.grcp.reactive.product.service.ProductService;

import java.util.Optional;
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
                .flatMap(product ->
                        ServerResponse.created(UriComponentsBuilder.fromPath("/product/" + product.getId()).build().toUri())
                                .contentType(APPLICATION_JSON)
                                .build());
    }

    public Mono<ServerResponse> getRetrieveProductById(ServerRequest serverRequest) {
        String id = getIdFromPathParam(serverRequest);
        return service.retrieveProduct(id)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getFindAllProducts(ServerRequest serverRequest) {
        return service.findAllProducts()
                .collectList()
                .flatMap(products -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(products));
    }

    public Mono<ServerResponse> putUpdateProduct(ServerRequest serverRequest) {
        String id = getIdFromPathParam(serverRequest);
        return serverRequest.bodyToMono(ProductVo.class)
                .flatMap(productVo -> service.updateProduct(id, productVo))
                .then(Mono.from(ServerResponse.ok().build()));
    }

    public Mono<ServerResponse> deleteProductById(ServerRequest serverRequest) {
        String id = getIdFromPathParam(serverRequest);
        return Mono.just(id)
                .flatMap(service::deleteProductById)
                .then(ServerResponse.ok().build());
    }

    private String getIdFromPathParam(ServerRequest serverRequest) {
        return Optional.ofNullable(serverRequest.pathVariable("id"))
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
