package main.kotlin.qscd.repositories

import eu.surething_project.core.LocationCertificate
import eu.surething_project.core.Time.TimeCase
import main.kotlin.qscd.models.LocationCertificateEntity
import main.kotlin.qscd.models.LocationCertificates
import main.kotlin.qscd.responses.exceptions.proofType.ProofTypeNotFoundException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class LocationCertificateRepository {

    fun addLocationCertificate(locationCertificate: LocationCertificate) = transaction {
        val locationVerification = locationCertificate.verification

        var endorsementIds = ""
        for (i in 0 until locationVerification.endorsementIdsCount) {
            endorsementIds += locationVerification.endorsementIdsList[i] + " "
        }
        endorsementIds.dropLast(1)

        val time = locationVerification.time
        val timeCase: TimeCase = time.timeCase

        LocationCertificateEntity.new {
            this.verifierId = locationVerification.verifierId
            this.claimId = locationVerification.claimId
            this.endorsementIds = endorsementIds

            // Init all as undefined
            this.timestamp1 = -1
            this.timestamp2 = -1
            this.epochId = ""
            this.timeValue = -1
            this.timeUnit = ""
            when (timeCase) {
                TimeCase.TIMESTAMP -> this.timestamp1 = time.timestamp.nanos
                TimeCase.INTERVAL -> {
                    this.timestamp1 = time.interval.begin.nanos
                    this.timestamp2 = time.interval.end.nanos
                }
                TimeCase.RELATIVETOEPOCH -> {
                    this.epochId = time.relativeToEpoch.epochId
                    this.timeValue = time.relativeToEpoch.timeValue.toInt()
                    this.timeUnit = time.relativeToEpoch.timeUnit
                }
                TimeCase.EMPTY -> {}
                TimeCase.TIME_NOT_SET -> {}
            }

            this.evidenceType = locationVerification.evidenceType
            this.evidence = locationVerification.evidence.toString()
            this.signatureValue = locationCertificate.verifierSignature.value.toString()
            this.signatureCryptoAlgo = locationCertificate.verifierSignature.cryptoAlgo
            this.signatureNonce = locationCertificate.verifierSignature.nonce.toInt()
        }
    }

    fun removeLocationCertificate(locationCertificateId: Int) = transaction {
        LocationCertificateEntity.findById(locationCertificateId)!!.delete()
    }

    fun getLocationCertificate(claimId: String) = transaction {
        LocationCertificateEntity.find(LocationCertificates.claimId eq claimId).firstOrNull() ?:
            throw ProofTypeNotFoundException(claimId)
    }
}