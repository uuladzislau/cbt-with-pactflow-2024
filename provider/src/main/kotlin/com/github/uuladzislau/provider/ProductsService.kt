package com.github.uuladzislau.provider

interface ProductsService {
    /**
     * Retrieve all products.
     */
    fun getAll(): List<ProductSummary>

    /**
     * Retrieve a product by its ID.
     * @return a [Product] if exists; `null` otherwise.
     */
    fun getById(id: Long): Product?
}