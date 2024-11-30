package io.kotest.extensions.spring.security

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.Components
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@SpringBootTest(classes = [SpringTestApplication::class])
@AutoConfigureWebTestClient
class SpringMockUserExtensionIntegrationTest(
   @Autowired private val webTestClient: WebTestClient,
) : DescribeSpec() {

   override fun extensions() = listOf(SpringExtension)

   init {
      describe("ADMIN") {
         extensions(SpringMockUserExtension(authorities = listOf("ADMIN")))
         it("should provide mock authentication") {
            webTestClient
               .get()
               .uri("/secure")
               .exchange()
               .expectStatus()
               .isOk
         }
      }
   }
}

