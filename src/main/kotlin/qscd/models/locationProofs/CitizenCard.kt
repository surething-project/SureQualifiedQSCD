package main.kotlin.qscd.models.locationProofs

import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.Sessions
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object CitizenCards : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val citizenCard = varchar("citizen_card", VARCHAR_LENGTH)
    val signature = binary("signature")
    val senderId = varchar("sender_id", VARCHAR_LENGTH)
    val session = reference("session_id", Sessions, onDelete = ReferenceOption.CASCADE)
    val validated = bool("validated")
}

// Database Object DAO
class CitizenCardEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<CitizenCardEntity>(CitizenCards)
    var citizenCard by CitizenCards.citizenCard
    var senderId by CitizenCards.senderId
    var signature by CitizenCards.signature
    var session by SessionEntity referencedOn CitizenCards.session
    var validated by CitizenCards.validated

    override fun toString(): String {
        return "Citizen Card($citizenCard, $senderId, $signature, ${session.sessionId}, $validated)"
    }
}