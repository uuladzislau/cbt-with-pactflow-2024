package com.github.uuladzislau.provider

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ProductsController(val productsService: ProductsService) {

    @GetMapping("/api/v1/products")
    fun get(): ResponseEntity<List<Product>> {
        println("WOW! Controller is invoked!")

        val products = productsService.getAll()

        return ResponseEntity.ok(products)
    }

    @GetMapping("/api/v1/products/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productsService.getById(id)

        return ResponseEntity.ok(product)
    }
}