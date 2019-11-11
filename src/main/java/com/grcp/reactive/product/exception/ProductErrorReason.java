package com.grcp.reactive.product.exception;

import org.springframework.http.HttpStatus;

public enum ProductErrorReason {

    NOT_FOUND("Product not found.", HttpStatus.NOT_FOUND);

    private String description;
    private HttpStatus status;

    ProductErrorReason(String description, HttpStatus status) {
        this.description = description;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
