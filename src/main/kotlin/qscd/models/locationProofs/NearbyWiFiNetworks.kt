package main.kotlin.qscd.models.locationProofs

import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.Sessions
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object NearbyWiFiNetworksTable : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val wiFiNetworks = varchar("wifi_networks", VARCHAR_LENGTH)
    val signature = binary("signature")
    val senderId = varchar("sender_id", VARCHAR_LENGTH)
    val session = reference("session_id", Sessions, onDelete = ReferenceOption.CASCADE)
    val validated = bool("validated")
}

// Database Object DAO
class NearbyWiFiNetworksEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<NearbyWiFiNetworksEntity>(NearbyWiFiNetworksTable)

    var wiFiNetworks by NearbyWiFiNetworksTable.wiFiNetworks
    var senderId by NearbyWiFiNetworksTable.senderId
    var signature by NearbyWiFiNetworksTable.signature
    var session by SessionEntity referencedOn NearbyWiFiNetworksTable.session
    var validated by NearbyWiFiNetworksTable.validated

    override fun toString(): String {
        return "Nearby WiFiNetworks($wiFiNetworks, $senderId, $signature, ${session.sessionId}, $validated)"
    }
}