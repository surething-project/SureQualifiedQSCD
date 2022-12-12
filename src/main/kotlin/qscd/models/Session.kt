package main.kotlin.qscd.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object Sessions : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val sessionId = varchar("session_id", VARCHAR_LENGTH)
    val proverId = varchar("prover_id", VARCHAR_LENGTH)
    val joined = bool("joined")
    val finished = bool("finished")
    val organization = reference("organization_id", Organizations, onDelete = ReferenceOption.CASCADE)
}

// Database Object DAO
class SessionEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SessionEntity>(Sessions)

    var sessionId by Sessions.sessionId
    var proverId by Sessions.proverId
    var joined by Sessions.joined
    var finished by Sessions.finished
    var organization by OrganizationEntity referencedOn Sessions.organization

    override fun toString(): String {
        return "Session($organization, $sessionId, $proverId, $joined, $finished)"
    }
}