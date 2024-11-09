package com.github.uuladzislau.provider

import org.springframework.stereotype.Service

@Service
class DummyProductsService : ProductsService {

    companion object {
        private val products = listOf<Product>(
            Product(1L, "Product 1"),
            Product(2L, "Product 2"),
            Product(3L, "Product 3")
        )
    }

    override fun getAll(): List<Product> {
        return products;
    }

    override fun getById(id: Long): Product? {
        return products.find { it.id == id }
    }
}