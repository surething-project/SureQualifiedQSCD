package main.kotlin.qscd.services

import main.kotlin.qscd.controllers.SenderAndSessionData
import main.kotlin.qscd.repositories.WiFiRepository
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.MeanTimeToRespond
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.MultipleBeacons
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.NearbyWiFiNetworks
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.RotatingSSID

class WiFiService(
    private val wiFiRepository: WiFiRepository,
    private val sessionService: SessionService
) {

    fun registerNearbyWiFiNetworks(nearbyWiFiNetworks: NearbyWiFiNetworks, senderId: String) {
        val session = sessionService.getDefaultSession()
        wiFiRepository.registerNearbyWiFiNetworks(nearbyWiFiNetworks, senderId, session)
    }

    fun registerWiFiConnection(wiFiConnection: MeanTimeToRespond, senderAndSessionData: SenderAndSessionData) {
        val session = sessionService.getSession(senderAndSessionData.sessionId)
        wiFiRepository.registerWiFiConnection(wiFiConnection, senderAndSessionData.senderId, session)
    }

    fun registerNearbyWiFiNetworks(nearbyWiFiNetworks: NearbyWiFiNetworks, senderAndSessionData: SenderAndSessionData) {
        val session = sessionService.getSession(senderAndSessionData.sessionId)
        wiFiRepository.registerNearbyWiFiNetworks(nearbyWiFiNetworks, senderAndSessionData.senderId, session)
    }

    fun registerWiFiRotatingSSID(rotatingSSID: RotatingSSID, senderAndSessionData: SenderAndSessionData) {
        val session = sessionService.getSession(senderAndSessionData.sessionId)
        wiFiRepository.registerWiFiRotatingSSID(rotatingSSID, senderAndSessionData.senderId, session)
    }

    fun registerWiFiMultipleBeacons(wiFiMultipleBeacons: MultipleBeacons, senderAndSessionData: SenderAndSessionData) {
        val session = sessionService.getSession(senderAndSessionData.sessionId)
        wiFiRepository.registerWiFiMultipleBeacons(wiFiMultipleBeacons, senderAndSessionData.senderId, session)
    }
}