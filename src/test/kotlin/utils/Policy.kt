package test.kotlin.utils

import pt.ulisboa.tecnico.qscd.contract.PolicyProto
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType

fun createProofsType(proofType: ProofsType.PROOF_TYPE): ProofsType {
    return ProofsType.newBuilder()
        .setProofType(proofType)
        .setPoints(DEFAULT_INT)
        .build()
}

fun createPolicy(): PolicyProto.Policy {
    val bluetoothConnection = createProofsType(ProofsType.PROOF_TYPE.BLUETOOTH_CONNECTION)
    val citizenCardSigned = createProofsType(ProofsType.PROOF_TYPE.CITIZEN_CARD_SIGNED)

    return PolicyProto.Policy.newBuilder()
        .addAllProofsType(listOf(bluetoothConnection, citizenCardSigned))
        .build()
}

fun createNewPolicy(): PolicyProto.Policy {
    val bluetoothConnection = createProofsType(ProofsType.PROOF_TYPE.BLUETOOTH_CONNECTION)
    val qrCode = createProofsType(ProofsType.PROOF_TYPE.QRCODE_SCAN)

    return PolicyProto.Policy.newBuilder()
        .addAllProofsType(listOf(bluetoothConnection, qrCode))
        .build()
}