package main.kotlin.qscd.application

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

class JWTAuthenticationConfig(
    environment: ApplicationEnvironment
) {
    private val secret = environment.config.property("jwt.secret").getString()
    private val validity = environment.config.property("jwt.validity").getString().toInt()
    private val algorithm = Algorithm.HMAC512(secret)

    val issuer = environment.config.property("jwt.issuer").getString()

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun createToken(sessionId: String, type: String, id: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("sessionId", sessionId)
        .withClaim(type, id)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validity)
}