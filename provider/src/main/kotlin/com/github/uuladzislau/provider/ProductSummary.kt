package com.github.uuladzislau.provider

/**
 * Represents a summary of a product.
 */
data class ProductSummary (
    val id: Long,
    val name: String,
    val price: Double,
) {
    companion object {
        /**
         * Creates a [ProductSummary] object from a [Product].
         */
        fun from(product: Product): ProductSummary {
            return ProductSummary(id = product.id, name = product.name, price = product.price)
        }
    }
}
