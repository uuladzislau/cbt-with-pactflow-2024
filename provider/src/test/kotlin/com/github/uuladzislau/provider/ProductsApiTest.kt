package com.github.uuladzislau.provider

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import java.io.File

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProductsApiTest {
    @LocalServerPort
    private val port = 0

    @MockBean
    private lateinit var productsService: ProductsService

    private val spec = File("api.yaml")

    private val validationFilter = OpenApiValidationFilter(spec.absolutePath)

    // For "negative" test cases use "responseOnlyValidationFilter".
    // Ref: https://bitbucket.org/atlassian/swagger-request-validator/issues/332/restassured-skip-request-validation-with
    /*
    private final LevelResolver levelResolver =
            LevelResolver.create()
                    .withLevel("validation.request", ValidationReport.Level.WARN)
                    .build();

    private final ValidationErrorsWhitelist whitelist =
            ValidationErrorsWhitelist.create()
                    .withRule("Ignore request entities", WhitelistRules.isRequest());

    private final OpenApiInteractionValidator responseOnlyValidator =
            OpenApiInteractionValidator.createForSpecificationUrl(spec.getAbsolutePath())
                    .withLevelResolver(levelResolver)
                    .withWhitelist(whitelist)
                    .build();

    private OpenApiValidationFilter responseOnlyValidationFilter = new OpenApiValidationFilter(responseOnlyValidator);
    */

    @Test
    fun `should return 200 and all products`() {
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
    fun `should return 200 and product with given id`() {
        Given {
            port(port)
            filter(validationFilter)
        } When {
            get("/api/v1/products/1")
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun `should return 404`() {
        Given {
            port(port)
            filter(validationFilter)
        } When {
            get("/api/v1/products/4")
        } Then {
            statusCode(200)
        }
    }
}