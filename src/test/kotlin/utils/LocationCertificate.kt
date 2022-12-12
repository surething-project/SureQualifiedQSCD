package test.kotlin.utils

import eu.surething_project.core.LocationCertificate

fun createLocationCertificate(): LocationCertificate {
    return LocationCertificate.newBuilder().apply {
        this.verification = createLocationVerification()
        this.verifierSignature = createSignature()
    }.build()
}