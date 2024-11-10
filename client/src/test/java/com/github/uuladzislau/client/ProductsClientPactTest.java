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

import java.util.List;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArrayMinLike;
import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
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

        var expected = List.of(
                new ProductSummary(1L, "Product 1", 200.0),
                new ProductSummary(2L, "Product 2", 100.0)
        );

        assertThat(products).isNotNull().containsAll(expected);
    }

    @Test
    @PactTestFor(pactMethod = "getProductById", pactVersion = PactSpecVersion.V3)
    void pactTestForGetProductById() {
        var product = this.productsClient.getProductDetails(1L);

        var expected = new Product(1L, "Product 1", 200.0D, ProductCategory.PHONES, List.of("Amstelveen"));

        assertThat(product).isNotNull().isEqualTo(expected);
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
                .body(newJsonArrayMinLike(1, a -> {
                    a.object(o -> {
                        o.numberType("id", 1);
                        o.stringType("name", "Product 1");
                        o.numberType("price", 200.0);
                    });
                    a.object(o -> {
                        o.numberType("id", 2);
                        o.stringType("name", "Product 2");
                        o.numberType("price", 100.0);
                    });
                }).build())
                .toPact();
    }

    @Pact(consumer = "client-app")
    private RequestResponsePact getProductById(PactDslWithProvider builder) {
        return builder
                .given("product exist")
                .uponReceiving("a request to get product details")
                .pathFromProviderState("/api/v1/products/${id}", "/api/v1/products/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonBody(b -> {
                    b.numberType("id", 1L);
                    b.stringType("name", "Product 1");
                    b.numberType("price", 200.0);
                    b.stringType("category", "PHONES");
                    b.array("stores", a -> {
                        a.stringValue("Amstelveen");
                    });
                }).build())
                .toPact();
    }
}
