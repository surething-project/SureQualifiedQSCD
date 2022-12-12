package main.kotlin.qscd.models.locationProofs

import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.Sessions
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object WiFiConnections : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val wiFiNetwork = varchar("wifi_network", VARCHAR_LENGTH)
    val sentTime = integer("sent_time")
    val receivedTime = integer("received_time")
    val signature = binary("signature")
    val senderId = varchar("sender_id", VARCHAR_LENGTH)
    val session = reference("session_id", Sessions, onDelete = ReferenceOption.CASCADE)
    val validated = bool("validated")
}

// Database Object DAO
class WiFiConnectionEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<WiFiConnectionEntity>(WiFiConnections)
    var wiFiNetwork by WiFiConnections.wiFiNetwork
    var sentTime by WiFiConnections.sentTime
    var receivedTime by WiFiConnections.receivedTime
    var senderId by WiFiConnections.senderId
    var signature by WiFiConnections.signature
    var session by SessionEntity referencedOn WiFiConnections.session
    var validated by WiFiConnections.validated

    override fun toString(): String {
        return "WiFi Connection($wiFiNetwork, $sentTime, $receivedTime, $senderId, $signature, ${session.sessionId}, $validated)"
    }
}