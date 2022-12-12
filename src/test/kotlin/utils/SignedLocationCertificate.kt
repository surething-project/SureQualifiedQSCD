package test.kotlin.utils

import pt.ulisboa.tecnico.transparency.ledger.contract.Ledger.SignedLocationCertificate

fun createSignedLocationCertificate(): SignedLocationCertificate {
   return SignedLocationCertificate.newBuilder().apply {
        this.id = DEFAULT_STRING
        this.locationCertificate = createLocationCertificate()
        this.signature = createSignature()
    }.build()
}