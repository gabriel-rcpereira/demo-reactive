package com.grcp.reactive.exception.handler;

import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;

import com.grcp.reactive.exception.model.ErrorResponse;
import com.grcp.reactive.product.exception.ProductException;
import java.util.function.Function;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(
            ErrorAttributes errorAttributes,
            ResourceProperties resourceProperties,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(buildProductPredicate(), this::renderProductErrorResponse);
    }

    private RequestPredicate buildProductPredicate() {
        return RequestPredicates.POST("/product")
                .or(RequestPredicates.DELETE("/product/*"))
                .or(RequestPredicates.PUT("/product/*"))
                .or(RequestPredicates.GET("/product/*"));
    }

    private Mono<ServerResponse> renderProductErrorResponse(ServerRequest serverRequest) {
        Throwable error = getError(serverRequest);
        return Mono.just(error)
                .cast(ProductException.class)
                .flatMap(getProductExceptionError())
                .switchIfEmpty(Mono.error(error));
    }

    private Function<ProductException, Mono<ServerResponse>> getProductExceptionError() {
        return productException -> ServerResponse.status(productException.getStatus())
                .bodyValue(ErrorResponse.builder().message(productException.getMessage()).build());
    }

}
