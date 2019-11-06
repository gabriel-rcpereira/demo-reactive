package com.grcp.reactive.product.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.grcp.reactive.product.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> routeProduct(ProductHandler handler){
        return route(POST("/product").and(accept(APPLICATION_JSON)), handler::postCreateProduct)
                .andRoute(GET("/product/{id}").and(accept(APPLICATION_JSON)), handler::getRetrieveProductById)
                .andRoute(GET("/product").and(accept(APPLICATION_JSON)), handler::getFindAllProducts)
                .andRoute(PUT("/product/{id}").and(accept(APPLICATION_JSON)), handler::putUpdateProduct)
                .andRoute(DELETE("/product/{id}"), handler::deleteProductById);
    }
}
