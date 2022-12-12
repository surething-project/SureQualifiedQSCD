package main.kotlin.qscd.models.locationProofs

import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.Sessions
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object CitizenCardsSigned : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val citizenCard = varchar("citizen_card", VARCHAR_LENGTH)
    val citizenCardSignature = binary("citizen_card_signature")
    val publicKey = varchar("public_key", VARCHAR_LENGTH * 5)
    val signature = binary("signature")
    val senderId = varchar("sender_id", VARCHAR_LENGTH)
    val session = reference("session_id", Sessions, onDelete = ReferenceOption.CASCADE)
    val validated = bool("validated")
}

// Database Object DAO
class CitizenCardSignedEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<CitizenCardSignedEntity>(CitizenCardsSigned)
    var citizenCard by CitizenCardsSigned.citizenCard
    var citizenCardSignature by CitizenCardsSigned.citizenCardSignature
    var publicKey by CitizenCardsSigned.publicKey
    var senderId by CitizenCardsSigned.senderId
    var signature by CitizenCardsSigned.signature
    var session by SessionEntity referencedOn CitizenCardsSigned.session
    var validated by CitizenCardsSigned.validated

    override fun toString(): String {
        return "Citizen Card Signed($citizenCard, $citizenCardSignature, $publicKey, $senderId, $signature, ${session.sessionId}, $validated)"
    }
}