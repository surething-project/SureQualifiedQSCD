package test.kotlin.utils

import pt.ulisboa.tecnico.transparency.ledger.contract.Ledger.SLCT

fun createSLCT(): SLCT {
    return SLCT.newBuilder().apply {
        this.logId = DEFAULT_INT
        this.timestamp = DEFAULT_LONG
        this.signedLocationCertificate = createSignedLocationCertificate()
    }.build()
}