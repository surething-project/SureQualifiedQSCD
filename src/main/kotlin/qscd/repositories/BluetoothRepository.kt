package main.kotlin.qscd.repositories

import main.kotlin.qscd.models.locationProofs.BluetoothConnectionEntity
import main.kotlin.qscd.models.locationProofs.NearbyBluetoothDevicesEntity
import main.kotlin.qscd.models.SessionEntity
import org.jetbrains.exposed.sql.transactions.transaction
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.BluetoothAP
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.BluetoothConnection
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.NearbyBluetoothDevices

class BluetoothRepository {

    private fun getBluetoothAps(bluetoothApsList: List<BluetoothAP>): String {
        val bluetoothApsListSorted = bluetoothApsList.sortedBy { it.bssid }

        var bluetoothAps = ""
        for (i in bluetoothApsListSorted.indices) {
            if (bluetoothApsListSorted[i].bssid === "") continue
            bluetoothAps += bluetoothApsListSorted[i].bssid + " "
        }
        return bluetoothAps.dropLast(1)
    }

    fun registerBluetoothConnection(bluetoothConnection: BluetoothConnection, senderId: String, sessionEntity: SessionEntity) = transaction {
        BluetoothConnectionEntity.new {
            this.proverToken = bluetoothConnection.proverToken
            this.verifierToken = bluetoothConnection.verifierToken
            this.senderId = senderId
            this.signature = bluetoothConnection.signature.toByteArray()
            this.session = sessionEntity
            this.validated = false
        }
    }

    fun registerNearbyBluetoothDevices(nearbyBluetoothDevices: NearbyBluetoothDevices, senderId: String, sessionEntity: SessionEntity) = transaction {
        val bluetoothAps = getBluetoothAps(nearbyBluetoothDevices.bluetoothApsList)

        NearbyBluetoothDevicesEntity.new {
            this.bluetoothAps = bluetoothAps
            this.senderId = senderId
            this.signature = nearbyBluetoothDevices.signature.toByteArray()
            this.session = sessionEntity
            this.validated = false
        }
    }
}