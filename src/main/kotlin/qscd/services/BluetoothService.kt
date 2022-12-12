package main.kotlin.qscd.services

import main.kotlin.qscd.controllers.SenderAndSessionData
import main.kotlin.qscd.repositories.BluetoothRepository
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.BluetoothConnection
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.NearbyBluetoothDevices

class BluetoothService(
    private val bluetoothRepository: BluetoothRepository,
    private val sessionService: SessionService
) {

    fun registerNearbyBluetoothDevices(nearbyBluetoothDevices: NearbyBluetoothDevices, senderId: String) {
        val session = sessionService.getDefaultSession()
        bluetoothRepository.registerNearbyBluetoothDevices(nearbyBluetoothDevices, senderId, session)
    }

    fun registerBluetoothConnection(bluetoothConnection: BluetoothConnection, senderAndSessionData: SenderAndSessionData) {
        val session = sessionService.getSession(senderAndSessionData.sessionId)
        bluetoothRepository.registerBluetoothConnection(bluetoothConnection, senderAndSessionData.senderId, session)
    }

    fun registerNearbyBluetoothDevices(nearbyBluetoothDevices: NearbyBluetoothDevices, senderAndSessionData: SenderAndSessionData) {
        val session = sessionService.getSession(senderAndSessionData.sessionId)
        bluetoothRepository.registerNearbyBluetoothDevices(nearbyBluetoothDevices, senderAndSessionData.senderId, session)
    }
}