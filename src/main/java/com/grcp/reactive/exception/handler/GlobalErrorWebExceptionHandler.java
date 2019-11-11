package com.grcp.reactive.exception.handler;

import com.grcp.reactive.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;

import com.grcp.reactive.exception.model.ErrorResponse;
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
        return RouterFunctions.route(buildProductPredicate(), this::renderErrorResponse);
    }

    private RequestPredicate buildProductPredicate() {
        return RequestPredicates.POST("/product")
                .or(RequestPredicates.DELETE("/product/*"))
                .or(RequestPredicates.PUT("/product/*"))
                .or(RequestPredicates.GET("/product/*"));
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
        Throwable error = getError(serverRequest);
        return Mono.just(error)
                .cast(BaseException.class)
                .flatMap(getCustomError())
                .switchIfEmpty(buildServerResponse(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage()));
    }

    private Function<BaseException, Mono<ServerResponse>> getCustomError() {
        return exception -> buildServerResponse(exception.getErrorStatus(), exception.getMessage());
    }

    private Mono<ServerResponse> buildServerResponse(HttpStatus status, String errorMessage) {
        return ServerResponse.status(status)
                .bodyValue(buildErrorResponse(errorMessage));
    }

    private ErrorResponse buildErrorResponse(String message) {
        return ErrorResponse.builder().message(message).build();
    }

}
