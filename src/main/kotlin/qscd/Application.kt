package main.kotlin.qscd

import io.ktor.server.application.*
import io.ktor.server.routing.*
import main.kotlin.qscd.application.JWTAuthenticationConfig
import main.kotlin.qscd.application.initDatabase
import main.kotlin.qscd.application.installFeatures
import main.kotlin.qscd.controllers.*
import main.kotlin.qscd.repositories.*
import main.kotlin.qscd.services.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val jwtAuthenticationConfig = JWTAuthenticationConfig(environment)

    val locationCertificateRepository = LocationCertificateRepository()
    val organizationRepository = OrganizationRepository()
    val policyRepository = PolicyRepository()
    val proofTypeRepository = ProofTypeRepository()
    val sessionRepository = SessionRepository()
    val bluetoothRepository = BluetoothRepository()
    val wiFiRepository = WiFiRepository()
    val citizenCardRepository = CitizenCardRepository()

    val certificateService = CertificateService(locationCertificateRepository)
    val policyService = PolicyService(policyRepository, proofTypeRepository)
    val organizationService = OrganizationService(organizationRepository, policyService)
    val proofTypeService = ProofTypeService(proofTypeRepository)
    val sessionService = SessionService(jwtAuthenticationConfig, organizationService, sessionRepository)
    val bluetoothService = BluetoothService(bluetoothRepository, sessionService)
    val wiFiService = WiFiService(wiFiRepository, sessionService)
    val citizenCardService = CitizenCardService(citizenCardRepository, sessionService)

    initDatabase(sessionService, policyService, organizationService, proofTypeService)

    installFeatures(jwtAuthenticationConfig, sessionService)

    routing {
        home()
        organizations(organizationService = organizationService)
        policies(policyService = policyService)
        locationCertificates(certificateService = certificateService)
        proofsType(proofTypeService = proofTypeService)
        sessions(sessionService = sessionService)
        bluetooths(bluetoothService = bluetoothService)
        wifis(wiFiService = wiFiService)
        citizenCards(citizenCardService = citizenCardService)
    }
}