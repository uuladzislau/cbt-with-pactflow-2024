package com.github.uuladzislau.provider

import org.springframework.stereotype.Service

@Service
class DummyProductsService : ProductsService {

    override fun getAll(): List<ProductSummary> {
        return products.map { ProductSummary.from(it) }
    }

    override fun getById(id: Long): Product? {
        return products.find { it.id == id }
    }

    companion object {
        private val products = listOf(
            Product(
                id = 1L,
                name = "MacBook",
                price = 200.0,
                category = ProductCategory.COMPUTERS,
                stores = listOf("Amsterdam")
            ),
            Product(
                id = 2L,
                name = "iPhone",
                price = 150.0,
                category = ProductCategory.PHONES,
                stores = listOf("Utrecht")
            ),
            Product(
                id = 3L,
                name = "Magic Mouse",
                price = 15.0,
                category = ProductCategory.ACCESSORIES,
                stores = listOf("Rotterdam", "Den Haag")
            ),
        )
    }
}