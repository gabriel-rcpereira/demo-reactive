package com.grcp.reactive.product.exception;

import com.grcp.reactive.exception.BaseException;

public class ProductException extends BaseException {

    public ProductException(ProductErrorReason errorReason) {
        super(errorReason.getDescription(), errorReason.getStatus());
    }
}
