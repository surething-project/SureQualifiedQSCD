package main.kotlin.qscd.responses.exceptions.locationCertificate

import main.kotlin.qscd.responses.exceptions.NotFoundException

data class LocationCertificateNotFoundException(val id: String) : NotFoundException("Location Certificate with id '$id' not found")