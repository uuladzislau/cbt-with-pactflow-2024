package com.github.uuladzislau.provider

interface ProductsService {
    fun getAll(): List<Product>
    fun getById(id: Long): Product?
}