package test.kotlin.utils

import com.google.protobuf.Any;
import com.google.protobuf.util.Timestamps.fromMillis
import eu.surething_project.core.LocationVerification
import eu.surething_project.core.Time
import eu.surething_project.core.wi_fi.WiFiNetworksEvidence

fun createLocationVerification(): LocationVerification {
    return LocationVerification.newBuilder().apply {
        this.verifierId = DEFAULT_STRING
        this.claimId = DEFAULT_STRING
        this.addEndorsementIds(DEFAULT_STRING)
        this.time = Time.newBuilder().setTimestamp(fromMillis(DEFAULT_LONG)).build()
        this.evidenceType = DEFAULT_STRING
        this.evidence = Any.pack(
            WiFiNetworksEvidence.newBuilder()
                .setId(DEFAULT_STRING)
                .addAps(WiFiNetworksEvidence.AP.newBuilder()
                    .setSsid(DEFAULT_STRING)
                    .setRssi(DEFAULT_STRING)
                .build())
            .build())
    }.build()
}