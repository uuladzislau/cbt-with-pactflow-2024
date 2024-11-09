package com.github.uuladzislau.client;

class ProductsClientError extends RuntimeException{
    ProductsClientError(Throwable cause) {
        super("An error occurred while retrieving products", cause);
    }
}
