package com.github.uuladzislau.provider

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
