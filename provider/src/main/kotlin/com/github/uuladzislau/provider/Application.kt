package com.github.uuladzislau.provider

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) = EngineMain.main(args)


val products = listOf(
    Product(1, "Product 1"),
    Product(2, "Product 2"),
    Product(3, "Product 3")
)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/products") {
            call.respond(products)
        }
    }

}
