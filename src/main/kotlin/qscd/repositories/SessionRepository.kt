package main.kotlin.qscd.repositories

import main.kotlin.qscd.models.OrganizationEntity
import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.Sessions
import main.kotlin.qscd.responses.exceptions.session.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class SessionRepository {

    companion object {
        const val STRING_LENGTH = 64
        val CHARPOOL : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    }

    fun generateRandomString(stringLength: Int): String {
        return (1..stringLength)
            .map { _ -> kotlin.random.Random.nextInt(0, CHARPOOL.size) }
            .map(CHARPOOL::get)
            .joinToString("")
    }

    fun addDefaultSession(organization: OrganizationEntity, finished: Boolean): SessionData = transaction {
        val defaultId = if (finished) "default" + "Finished" else "default"

        SessionEntity.new {
            this.sessionId = defaultId
            this.proverId = defaultId
            this.joined = false
            this.finished = finished
            this.organization = organization
        }

        SessionData(defaultId, defaultId)
    }

    fun addSession(organization: OrganizationEntity): SessionData = transaction {
        val sessionId = generateRandomString(STRING_LENGTH)
        val proverId = organization.name + generateRandomString(STRING_LENGTH - organization.name.length)

        SessionEntity.new {
            this.sessionId = sessionId
            this.proverId = proverId
            this.joined = false
            this.finished = false
            this.organization = organization
        }

        SessionData(sessionId, proverId)
    }

    fun joinSession(sessionId: String) = transaction {
        val session = getSession(sessionId)
        if (session.joined) throw SessionAlreadyJoined(sessionId)
        session.joined = true
    }

    fun finishSession(sessionId: String) = transaction {
        val session = getSession(sessionId)
        if (session.finished) throw SessionAlreadyFinished(sessionId)
        session.finished = true
    }

    fun getDefaultSession() = transaction {
        SessionEntity.find(Sessions.sessionId.eq("default")).firstOrNull() ?:
            throw SessionNotFoundException("default")
    }

    fun getSession(sessionId: String) = transaction {
        SessionEntity.find(Sessions.sessionId.eq(sessionId)).firstOrNull() ?:
            throw SessionNotFoundException(sessionId)
    }

    fun getOpenSession(organization: OrganizationEntity) = transaction {
        SessionEntity.find(Sessions.organization.eq(organization.id)).firstOrNull { session ->
            !session.finished
        } ?:
            throw SessionNotFoundForOrganizationException(organization.name)
    }

    fun getProver(proverId: String) = transaction {
        SessionEntity.find(Sessions.proverId eq proverId).firstOrNull() ?:
            throw ProverNotFoundException(proverId)
    }
}

data class SessionData(var sessionId: String, val proverId: String)
