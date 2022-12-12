package test.kotlin.utils

import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.CitizenCard
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.CitizenCardSigned

fun createCitizenCard(): CitizenCard {
    return CitizenCard.newBuilder()
        .setId(DEFAULT_STRING)
        .setSignature(createSignature())
        .build()
}

fun createCitizenCardSigned(): CitizenCardSigned {
    return CitizenCardSigned.newBuilder()
        .setCitizenCard(createCitizenCard())
        .setSignature(createSignature())
        .setPublicKey(DEFAULT_STRING)
        .build()
}