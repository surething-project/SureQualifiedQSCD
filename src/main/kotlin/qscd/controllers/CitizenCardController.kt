package main.kotlin.qscd.controllers

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.kotlin.qscd.application.*
import main.kotlin.qscd.responses.ErrorResponse
import main.kotlin.qscd.responses.SuccessResponse
import main.kotlin.qscd.services.CitizenCardService
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.CitizenCard
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.CitizenCardSigned

fun Route.citizenCards(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    citizenCardService: CitizenCardService
) {

    post<RegisterSimpleCitizenCardNoAuth> {
        withContext(dispatcher) {
            val citizenCard = CitizenCard.parseFrom(call.receive<ByteArray>())
            val senderId = call.parameters["senderId"]

            if (senderId != null) {
                citizenCardService.registerCitizenCard(citizenCard, senderId)
                call.respond(SuccessResponse(data = true))
            }
            call.respond(ErrorResponse(message = "Sender Id is necessary"))
        }
    }

    authenticate("token-auth") {
        post<RegisterSimpleCitizenCard> {
            withContext(dispatcher) {
                val citizenCard = CitizenCard.parseFrom(call.receive<ByteArray>())
                val senderAndSessionData = getSenderAndSessionIds(call)
                citizenCardService.registerCitizenCard(citizenCard, senderAndSessionData)
                call.respond(SuccessResponse(data = true))
            }
        }

        post<RegisterSignedCitizenCard> {
            withContext(dispatcher) {
                val citizenCardSigned = CitizenCardSigned.parseFrom(call.receive<ByteArray>())
                val senderAndSessionData = getSenderAndSessionIds(call)
                citizenCardService.registerCitizenCardSigned(citizenCardSigned, senderAndSessionData)
                call.respond(SuccessResponse(data = true))
            }
        }
    }
}