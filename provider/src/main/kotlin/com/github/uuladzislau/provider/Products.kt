package com.github.uuladzislau.provider

/**
 * Represents a summary of a product.
 */
data class ProductSummary (val id: Long, val name: String, val price: Double)

/**
 * Represents a full product information with all the details.
 */
data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val category: ProductCategory,
    val stores: List<String>
)

/**
 * Represents a product's category.
 */
enum class ProductCategory { PHONES, COMPUTERS, ACCESSORIES }