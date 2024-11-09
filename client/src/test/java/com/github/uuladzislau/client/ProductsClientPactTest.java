package com.github.uuladzislau.client;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@PactConsumerTest
@PactTestFor(providerName = "products-service")
class ProductsClientPactTest {

    private ProductsClient productsClient;

    @BeforeEach
    void setUp(MockServer mockServer) {
        var config = new ProductsClientConfig(mockServer.getUrl());
        this.productsClient = new ProductsClient(config);
    }

    @Test
    @PactTestFor(pactMethod = "getAllProducts", pactVersion = PactSpecVersion.V3)
    void pactTestForGetAllProducts() {
        var products = this.productsClient.getProducts();

        assertThat(products)
                .isNotNull()
                .isNotEmpty();
    }

    @Pact(consumer = "client-app")
    private RequestResponsePact getAllProducts(PactDslWithProvider builder) {
        // @formatter:off
        return builder
            .given("products exist")
            .uponReceiving("a request to get all products")
                .path("/api/v1/products")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body(PactDslJsonArray
                    .arrayEachLike()
                        .numberType("id", 1)
                        .stringType("name", "Product 1")
                        .closeObject()
                    .object()
                        .numberType("id", 2)
                        .stringType("name", "Product 2")
                    .closeObject()
                )
            .toPact();
        // @formatter:on
    }
}
