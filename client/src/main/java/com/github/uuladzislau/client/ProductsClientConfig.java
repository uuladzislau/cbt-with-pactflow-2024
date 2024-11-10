package com.github.uuladzislau.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ProductsClientConfig {

    @ConfigurationProperties("products")
    record Properties(String baseUrl) {}

    @Bean
    RestClient productsRestClient(Properties properties) {
        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }

    @Bean
    ProductsClient productsClient(RestClient productsRestClient) {
        RestClientAdapter adapter = RestClientAdapter.create(productsRestClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ProductsClient.class);
    }
}