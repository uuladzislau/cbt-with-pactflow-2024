package com.github.uuladzislau.provider

import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import au.com.dius.pact.provider.junitsupport.AllowOverridePactUrl
import au.com.dius.pact.provider.junitsupport.IgnoreMissingStateChange
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.loader.PactBroker
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort


@Provider("products-service")
@PactBroker(
    url = "\${PACT_BROKER_BASE_URL}",
    providerBranch = "main",
    authentication = PactBrokerAuth(token = "\${PACT_BROKER_TOKEN}"),
)
@AllowOverridePactUrl
@IgnoreMissingStateChange
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsPactTest {

    @LocalServerPort
    private val port = 0

    @BeforeEach
    fun setupTestTarget(context: PactVerificationContext) {
        context.target = HttpTestTarget("localhost", this.port)
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }
}