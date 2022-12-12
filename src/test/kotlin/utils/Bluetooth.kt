package test.kotlin.utils

import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.BluetoothAP
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.BluetoothConnection
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.NearbyBluetoothDevices

fun createBluetoothConnection(): BluetoothConnection {
    return BluetoothConnection.newBuilder()
        .setProverToken(DEFAULT_STRING)
        .setVerifierToken(DEFAULT_STRING)
        .setSignature(createSignature())
        .build()
}

fun createNearbyBluetoothDevices(): NearbyBluetoothDevices {
    return NearbyBluetoothDevices.newBuilder()
        .addBluetoothAps(BluetoothAP.newBuilder().setBssid(DEFAULT_STRING).build())
        .setSignature(createSignature())
        .build()
}