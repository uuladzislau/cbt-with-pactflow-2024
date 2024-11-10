package com.github.uuladzislau.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "/api/v1/products", accept = "application/json")
interface ProductsClient {

    @GetExchange()
    List<ProductSummary> getProductSummaries();

    @GetExchange("/{id}")
    Product getProductDetails(@PathVariable Long id);
}
