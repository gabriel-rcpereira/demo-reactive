package com.grcp.reactive.product.exception;


import org.springframework.http.HttpStatus;

public class ProductException extends Exception {

    private ProductErrorReason errorReason;

    public ProductException(ProductErrorReason errorReason) {
        super(errorReason.getDescription());
    }

    public HttpStatus getStatus(){
        return errorReason.getStatus();
    }
}
