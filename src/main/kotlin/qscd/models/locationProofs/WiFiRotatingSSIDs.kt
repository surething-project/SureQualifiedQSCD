package main.kotlin.qscd.models.locationProofs

import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.Sessions
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object RotatingSSIDsTable : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val wiFiNetworks = varchar("wifi_networks", VARCHAR_LENGTH)
    val signature = binary("signature")
    val senderId = varchar("sender_id", VARCHAR_LENGTH)
    val session = reference("session_id", Sessions, onDelete = ReferenceOption.CASCADE)
    val validated = bool("validated")
}

// Database Object DAO
class WiFiRotatingSSIDsEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<WiFiRotatingSSIDsEntity>(RotatingSSIDsTable)

    var wiFiNetworks by RotatingSSIDsTable.wiFiNetworks
    var senderId by RotatingSSIDsTable.senderId
    var signature by RotatingSSIDsTable.signature
    var session by SessionEntity referencedOn RotatingSSIDsTable.session
    var validated by RotatingSSIDsTable.validated

    override fun toString(): String {
        return "Rotating SSIDs($wiFiNetworks, $senderId, $signature, ${session.sessionId}, $validated)"
    }
}