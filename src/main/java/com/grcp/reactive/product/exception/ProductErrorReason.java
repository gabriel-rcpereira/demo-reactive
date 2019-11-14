package com.grcp.reactive.product.exception;

import org.springframework.http.HttpStatus;

public enum ProductErrorReason {

    PRODUCT_NOT_FOUND("Product was not found.", HttpStatus.NOT_FOUND),
    INVALID_ID_PARAMETER("Invalid product id parameter.", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_ALREADY_EXISTS("Product name already exists.", HttpStatus.CONFLICT);

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
