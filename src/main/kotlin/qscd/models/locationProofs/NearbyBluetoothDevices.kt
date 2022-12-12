package main.kotlin.qscd.models.locationProofs

import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.Sessions
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object NearbyBluetoothDevicesTable : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val bluetoothAps = varchar("bluetooth_aps", VARCHAR_LENGTH)
    val signature = binary("signature")
    val senderId = varchar("sender_id", VARCHAR_LENGTH)
    val session = reference("session_id", Sessions, onDelete = ReferenceOption.CASCADE)
    val validated = bool("validated")
}

// Database Object DAO
class NearbyBluetoothDevicesEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<NearbyBluetoothDevicesEntity>(NearbyBluetoothDevicesTable)

    var bluetoothAps by NearbyBluetoothDevicesTable.bluetoothAps
    var senderId by NearbyBluetoothDevicesTable.senderId
    var signature by NearbyBluetoothDevicesTable.signature
    var session by SessionEntity referencedOn NearbyBluetoothDevicesTable.session
    var validated by NearbyBluetoothDevicesTable.validated

    override fun toString(): String {
        return "Nearby Bluetooth Devices($bluetoothAps, $senderId, $signature, ${session.sessionId}, $validated)"
    }
}