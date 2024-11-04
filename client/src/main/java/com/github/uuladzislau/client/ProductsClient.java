package com.github.uuladzislau.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
class ProductsClient {

    private static final Logger log = LoggerFactory.getLogger(ProductsClient.class);

    private final String baseUrl;

    @Autowired
    ProductsClient(ProductsClientConfig config) {
        this.baseUrl = config.baseUrl();
    }

    String getProducts() {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/api/v1/products")).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("Response: {}", response.body());

            return response.body();
        } catch (IOException e) {
            throw new ProductsClientError(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProductsClientError(e);
        }
    }
}
