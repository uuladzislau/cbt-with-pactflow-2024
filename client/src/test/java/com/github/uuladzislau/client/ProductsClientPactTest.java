package com.github.uuladzislau.client;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArray;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {"products.base-url=http://localhost:49700"}
)
@PactConsumerTest
@PactTestFor(providerName = "products-service")
@MockServerConfig(port = "49700")
class ProductsClientPactTest {

    @Autowired
    private ProductsClient productsClient;


    @BeforeEach
    void setUp(MockServer mockServer) {
        System.out.println("Mock server URL: " + mockServer.getUrl());
    }

    @Test
    @PactTestFor(pactMethod = "getAllProducts", pactVersion = PactSpecVersion.V3)
    void pactTestForGetAllProducts(MockServer mockServer) {
        var products = this.productsClient.getProductSummaries();

        assertThat(products)
                .isNotNull()
                .isNotEmpty();
    }

    @Pact(consumer = "client-app")
    private RequestResponsePact getAllProducts(PactDslWithProvider builder) {
        return builder
                .given("products exist")
                .uponReceiving("a request to get all products")
                .path("/api/v1/products")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArray(a -> {
                    a.object(o -> {
                        o.numberType("id", 1);
                        o.stringType("name", "Product 1");
                    });
                    a.object(o -> {
                        o.numberType("id", 2);
                        o.stringType("name", "Product 2");
                    });
                }).build())
                .toPact();
    }
}
