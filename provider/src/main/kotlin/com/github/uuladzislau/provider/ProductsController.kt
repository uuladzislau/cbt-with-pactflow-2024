package com.github.uuladzislau.provider

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductsController(val productsService: ProductsService) {

    @GetMapping
    fun get(): ResponseEntity<List<Product>> {
        val products = productsService.getAll()

        return ResponseEntity.ok(products)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productsService.getById(id)

        if (product != null) {
            return ResponseEntity.ok(product)
        }

        return ResponseEntity.notFound().build()
    }
}