package main.kotlin.qscd.services

import main.kotlin.qscd.application.JWTAuthenticationConfig
import main.kotlin.qscd.models.OrganizationEntity
import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.repositories.SessionData
import main.kotlin.qscd.repositories.SessionRepository
import main.kotlin.qscd.responses.exceptions.session.InvalidProverSessionException

class SessionService(
    private val jwtAuthenticationConfig: JWTAuthenticationConfig,
    private val organizationService: OrganizationService,
    private val sessionRepository: SessionRepository
) {
    fun createDefaultSession(): SessionData {
        val organization = organizationService.getOrganization("kiosk")
        return sessionRepository.addDefaultSession(organization, false)
    }

    fun createDefaultFinishedSession(): SessionData {
        val organization = organizationService.getOrganization("kiosk")
        return sessionRepository.addDefaultSession(organization, true)
    }

    fun createSession(organizationName: String): SessionData {
        val organization = organizationService.getOrganization(organizationName)
        return sessionRepository.addSession(organization)
    }

    fun joinSession(sessionId: String, proverId: String): String {
        getSession(sessionId)
        getProver(sessionId, proverId)
        sessionRepository.joinSession(sessionId)
        return createJWTAuthenticationToken(sessionId, "prover", proverId)
    }

    fun finishSession(sessionId: String) {
        getSession(sessionId)
        sessionRepository.finishSession(sessionId)
    }

    fun getDefaultSession(): SessionEntity {
        return sessionRepository.getDefaultSession()
    }

    fun getSession(sessionId: String): SessionEntity {
        return sessionRepository.getSession(sessionId)
    }

    fun getOpenSession(organizationName: String): String {
        val organization = getOrganization(organizationName)
        return sessionRepository.getOpenSession(organization).sessionId
    }

    fun getOrganization(organizationName: String): OrganizationEntity {
        return organizationService.getOrganization(organizationName)
    }

    fun getProver(sessionId: String, proverId: String): SessionEntity {
        val prover = sessionRepository.getProver(proverId)
        if (prover.sessionId != sessionId) throw InvalidProverSessionException(sessionId, proverId)

        return prover
    }

    fun createJWTAuthenticationToken(sessionId: String, type: String, id: String): String {
        return jwtAuthenticationConfig.createToken(sessionId, type, id)
    }
}
