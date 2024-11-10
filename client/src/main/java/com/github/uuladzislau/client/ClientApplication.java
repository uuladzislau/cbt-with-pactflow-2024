package com.github.uuladzislau.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Comparator;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
class ClientApplication {

    private final Logger log = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(ClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    @Profile("production")
    CommandLineRunner run(final ProductsClient client) {
        return args -> {
            List<ProductSummary> summaries = client.getProductSummaries();

            log.info("Summaries: {}", summaries);

            ProductSummary cheapest =
                    summaries.stream()
                            .min(Comparator.comparing(ProductSummary::price))
                            .orElseThrow();

            log.info("Cheapest: \"{}\" costs only ${}", cheapest.name(), cheapest.price());

            Product product = client.getProductDetails(cheapest.id());

            log.info("Cheapest product has category: \"{}\", and available at stores in: {}", product.category(), product.stores());
        };
    }
}
