package com.github.uuladzislau.provider

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsController(val productsService: ProductsService) {

    @GetMapping("/api/v1/products")
    fun get(): ResponseEntity<List<Product>> {
        println("received request /api/v1/products")

        val products = productsService.getAll()

        println("response: 200 $products")

        return ResponseEntity.ok(products)
    }

    @GetMapping("/api/v1/products/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Product> {
        println("received request /api/v1/products/$id")

        val product = productsService.getById(id)

        if (product == null) {
            println("response: 404 Not Found")
            return ResponseEntity.notFound().build()
        }

        println("response: 200 $product")

        return ResponseEntity.ok(product)
    }
}