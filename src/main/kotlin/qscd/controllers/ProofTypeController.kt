package main.kotlin.qscd.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.kotlin.qscd.application.AddProofType
import main.kotlin.qscd.responses.SuccessResponse
import main.kotlin.qscd.services.ProofTypeService
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType

fun Route.proofsType(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    proofTypeService: ProofTypeService
) {
    post<AddProofType> {
        withContext(dispatcher) {
            val proofType = ProofsType.parseFrom(call.receive<ByteArray>())
            proofTypeService.registerProofType(proofType)
            call.respond(SuccessResponse(data = true))
        }
    }
}
