package main.kotlin.qscd.repositories

import main.kotlin.qscd.models.locationProofs.*
import main.kotlin.qscd.models.SessionEntity
import org.jetbrains.exposed.sql.transactions.transaction
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.MeanTimeToRespond
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.MultipleBeacons
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.NearbyWiFiNetworks
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.RotatingSSID

class WiFiRepository {

    private fun getWiFiNetworks(wiFiNetworksList: List<LocationProofsProto.WiFiNetwork>): String {
        val wiFiNetworksListSorted = wiFiNetworksList.sortedBy { it.ssid }

        var wiFiNetworks = ""
        for (i in wiFiNetworksListSorted.indices) {
            if (wiFiNetworksListSorted[i].ssid === "") continue
            wiFiNetworks += wiFiNetworksListSorted[i].ssid + " "
        }

        return wiFiNetworks.dropLast(1)
    }

    fun registerWiFiConnection(wiFiConnection: MeanTimeToRespond, senderId: String, sessionEntity: SessionEntity) = transaction {
        WiFiConnectionEntity.new {
            this.wiFiNetwork = wiFiConnection.wifiNetwork.ssid
            this.sentTime = wiFiConnection.sentTime
            this.receivedTime = wiFiConnection.receivedTime
            this.senderId = senderId
            this.signature = wiFiConnection.signature.toByteArray()
            this.session = sessionEntity
            this.validated = false
        }
    }

    fun registerNearbyWiFiNetworks(nearbyWiFiNetworks: NearbyWiFiNetworks, senderId: String, sessionEntity: SessionEntity) = transaction {
        val wiFiNetworks = getWiFiNetworks(nearbyWiFiNetworks.wiFiNetworksList)

        NearbyWiFiNetworksEntity.new {
            this.wiFiNetworks = wiFiNetworks
            this.senderId = senderId
            this.signature = nearbyWiFiNetworks.signature.toByteArray()
            this.session = sessionEntity
            this.validated = false
        }
    }

    fun registerWiFiRotatingSSID(rotatingSSID: RotatingSSID, senderId: String, sessionEntity: SessionEntity) = transaction {
        val wiFiNetworks = getWiFiNetworks(rotatingSSID.wiFiNetworksList)

        WiFiRotatingSSIDsEntity.new {
            this.wiFiNetworks = wiFiNetworks
            this.senderId = senderId
            this.signature = rotatingSSID.signature.toByteArray()
            this.session = sessionEntity
            this.validated = false
        }
    }

    fun registerWiFiMultipleBeacons(wiFiMultipleBeacons: MultipleBeacons, senderId: String, sessionEntity: SessionEntity) = transaction {
        val wiFiNetworks = getWiFiNetworks(wiFiMultipleBeacons.wiFiNetworksList)

        WiFiMultipleBeaconsEntity.new {
            this.wiFiNetworks = wiFiNetworks
            this.senderId = senderId
            this.signature = wiFiMultipleBeacons.signature.toByteArray()
            this.session = sessionEntity
            this.validated = false
        }
    }
}