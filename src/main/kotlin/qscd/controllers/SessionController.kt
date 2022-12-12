package main.kotlin.qscd.controllers

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.kotlin.qscd.application.CreateSession
import main.kotlin.qscd.application.FinishSession
import main.kotlin.qscd.application.GetOrganizationSession
import main.kotlin.qscd.application.JoinSession
import main.kotlin.qscd.responses.SuccessResponse
import main.kotlin.qscd.services.SessionService
import pt.ulisboa.tecnico.qscd.contract.OrganizationProto.Organization
import pt.ulisboa.tecnico.qscd.contract.ProverProto.Prover

fun Route.sessions(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    sessionService: SessionService
) {
    post<CreateSession> {
        withContext(dispatcher) {
            val organization = Organization.parseFrom(call.receive<ByteArray>())
            val sessionData = sessionService.createSession(organization.name)
            val token = sessionService.createJWTAuthenticationToken(sessionData.sessionId, "organization", organization.name)
            call.respond(SuccessResponse(data = sessionData, token = token))
        }
    }

    put<JoinSession> {
        withContext(dispatcher) {
            val prover = Prover.parseFrom(call.receive<ByteArray>())
            val sessionId = call.parameters["id"]
            val token = sessionService.joinSession(sessionId!!, prover.id)
            call.respond(SuccessResponse(data = true, token = token))
        }
    }

    authenticate("token-auth") {
        get<GetOrganizationSession> {
            withContext(dispatcher) {
                val organizationName = call.parameters["organizationName"]
                
                val sessionId = sessionService.getOpenSession(organizationName!!)
                call.respond(SuccessResponse(data = sessionId))
            }
        }

        put<FinishSession> {
            withContext(dispatcher) {
                val senderAndSessionData = getSenderAndSessionIds(call)

                sessionService.finishSession(senderAndSessionData.sessionId)
                call.respond(SuccessResponse(data = true))
            }
        }
    }
}

fun getSenderAndSessionIds(call: ApplicationCall): SenderAndSessionData {
    val principal = call.principal<JWTPrincipal>()

    // Get the organization name / prover id
    val organizationName = principal!!.payload.getClaim("organization").asString()
    val proverId = principal.payload.getClaim("prover").asString()
    val senderId = if (!organizationName.isNullOrBlank()) organizationName else proverId

    val sessionId = principal.payload.getClaim("sessionId").asString()

    return SenderAndSessionData(senderId, sessionId)
}

data class SenderAndSessionData(val senderId: String, val sessionId: String)
