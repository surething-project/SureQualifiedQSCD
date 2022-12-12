package main.kotlin.qscd.services

import main.kotlin.qscd.repositories.LocationCertificateRepository
import pt.ulisboa.tecnico.transparency.ledger.contract.Ledger.SLCT

class CertificateService(
    private val locationCertificateRepository: LocationCertificateRepository
) {
    fun registerLocationCertificate(slct: SLCT) {
        // Validate Verifier Identity
        // Check parameters
        // Check signature
        // Check if exists

        println(slct)
        /*if (slct.timestamp) {
            throw
        }*/
        val signedLocationCertificate = slct.signedLocationCertificate
        locationCertificateRepository.addLocationCertificate(signedLocationCertificate.locationCertificate)
    }
}