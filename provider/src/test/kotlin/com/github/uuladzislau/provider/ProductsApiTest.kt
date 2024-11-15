package com.github.uuladzislau.provider

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import java.io.File

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsApiTest {
    @LocalServerPort
    private val port = 0

    private val spec = File("api.yaml")

    private val validationFilter = OpenApiValidationFilter(spec.absolutePath)

    @Test
    fun `should return 200 OK and all product summaries`() {
        Given {
            port(port)
            filter(validationFilter)
        } When {
            get("/api/v1/products")
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun `should return 200 OK and product with given id and details`() {
        Given {
            port(port)
            filter(validationFilter)
        } When {
            get("/api/v1/products/1")
        } Then {
            statusCode(200)
            body(
                "id", equalTo(1),
                "name", equalTo("MacBook"),
                "price", equalTo(200.0F),
                "category", equalTo("COMPUTERS"),
                "stores", contains("Amsterdam")
            )
        }
    }

    @Test
    fun `should return 404 Not Found`() {
        Given {
            port(port)
            filter(validationFilter)
        } When {
            get("/api/v1/products/4")
        } Then {
            statusCode(404)
        }
    }
}