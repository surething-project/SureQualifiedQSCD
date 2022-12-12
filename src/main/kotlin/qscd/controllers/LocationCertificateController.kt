package main.kotlin.qscd.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.resources.post
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.kotlin.qscd.application.RegisterLocationCertificate
import main.kotlin.qscd.responses.SuccessResponse
import main.kotlin.qscd.services.CertificateService
import pt.ulisboa.tecnico.transparency.ledger.contract.Ledger.SLCT

fun Route.locationCertificates(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    certificateService: CertificateService
) {
    post<RegisterLocationCertificate> {
        withContext(dispatcher) {
            val slct = SLCT.parseFrom(call.receive<ByteArray>())
            certificateService.registerLocationCertificate(slct)
            call.respond(SuccessResponse(data = true))
        }
    }
}
