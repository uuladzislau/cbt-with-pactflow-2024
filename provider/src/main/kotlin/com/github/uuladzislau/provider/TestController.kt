package com.github.uuladzislau.provider

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class TestController {

    companion object {
        private val products = listOf<Product>(
            Product(UUID.randomUUID(), "Product 1"),
            Product(UUID.randomUUID(), "Product 2"),
            Product(UUID.randomUUID(), "Product 3")
        )
    }

    @GetMapping("/api/v1/products")
    fun get(): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(products);
    }
}