package main.kotlin.qscd.models.locationProofs

import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.Sessions
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object BluetoothConnections : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val proverToken = varchar("prover_token", VARCHAR_LENGTH)
    val verifierToken = varchar("verifier_token", VARCHAR_LENGTH)
    val senderId = varchar("sender_id", VARCHAR_LENGTH)
    val signature = binary("signature")
    val session = reference("session_id", Sessions, onDelete = ReferenceOption.CASCADE)
    val validated = bool("validated")
}

// Database Object DAO
class BluetoothConnectionEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<BluetoothConnectionEntity>(BluetoothConnections)
    var proverToken by BluetoothConnections.proverToken
    var verifierToken by BluetoothConnections.verifierToken
    var senderId by BluetoothConnections.senderId
    var signature by BluetoothConnections.signature
    var session by SessionEntity referencedOn BluetoothConnections.session
    var validated by BluetoothConnections.validated

    override fun toString(): String {
        return "Bluetooth Connection($proverToken, $verifierToken, $senderId, $signature, ${session.sessionId}, $validated)"
    }
}