package com.github.uuladzislau.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("products")
record ProductsClientConfig(String baseUrl) {}
