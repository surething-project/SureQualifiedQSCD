package test.kotlin.utils

import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.MeanTimeToRespond
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.MultipleBeacons
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.NearbyWiFiNetworks
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.RotatingSSID
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.WiFiNetwork

fun createWiFiConnection(): MeanTimeToRespond {
    return MeanTimeToRespond.newBuilder()
        .setWifiNetwork(WiFiNetwork.newBuilder().setSsid(DEFAULT_STRING).build())
        .setSentTime(DEFAULT_INT)
        .setReceivedTime(DEFAULT_INT)
        .setSignature(createSignature())
        .build()
}

fun createNearbyWiFiNetworks(): NearbyWiFiNetworks {
    return NearbyWiFiNetworks.newBuilder()
        .addWiFiNetworks(WiFiNetwork.newBuilder().setSsid(DEFAULT_STRING).build())
        .setSignature(createSignature())
        .build()
}

fun createWiFiRotatingSSID(): RotatingSSID {
    return RotatingSSID.newBuilder()
        .addWiFiNetworks(WiFiNetwork.newBuilder().setSsid(DEFAULT_STRING).build())
        .setSignature(createSignature())
        .build()
}

fun createWiFiMultipleBeacons(): MultipleBeacons {
    return MultipleBeacons.newBuilder()
        .addWiFiNetworks(WiFiNetwork.newBuilder().setSsid(DEFAULT_STRING).build())
        .setSignature(createSignature())
        .build()
}