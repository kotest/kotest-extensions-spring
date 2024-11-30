package io.kotest.extensions.spring.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SpringTestApplication

@RestController
@RequestMapping("/secure")
class TestSecurityController {

   @PreAuthorize("hasAuthority('ADMIN')")
   @GetMapping
   @ResponseStatus(HttpStatus.OK)
   suspend fun test() = "Ok"
}
