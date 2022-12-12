package main.kotlin.qscd.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// Table Object
object LocationCertificates : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val verifierId = varchar("verifier_id", VARCHAR_LENGTH)
    val claimId = varchar("claim_id", VARCHAR_LENGTH)
    val endorsementIds = varchar("endorsement_ids", VARCHAR_LENGTH)
    val timestamp1 = integer("timestamp1")
    val timestamp2 = integer("timestamp2")
    val epochId = varchar("epoch_id", VARCHAR_LENGTH)
    val timeValue = integer("time_value")
    val timeUnit = varchar("time_unit", VARCHAR_LENGTH)
    val evidenceType = varchar("evidence_type", VARCHAR_LENGTH)
    val evidence = varchar("evidence", VARCHAR_LENGTH)
    val signatureValue = varchar("signature_value", VARCHAR_LENGTH)
    val signatureCryptoAlgo = varchar("signature_crypto_algo", VARCHAR_LENGTH)
    val signatureNonce = integer("signature_nonce")
}

// Database Object DAO
class LocationCertificateEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<LocationCertificateEntity>(LocationCertificates)

    var verifierId by LocationCertificates.verifierId
    var claimId by LocationCertificates.claimId
    var endorsementIds by LocationCertificates.endorsementIds
    var timestamp1 by LocationCertificates.timestamp1
    var timestamp2 by LocationCertificates.timestamp2
    var epochId by LocationCertificates.epochId
    var timeValue by LocationCertificates.timeValue
    var timeUnit by LocationCertificates.timeUnit
    var evidenceType by LocationCertificates.evidenceType
    var evidence by LocationCertificates.evidence
    var signatureValue by LocationCertificates.signatureValue
    var signatureCryptoAlgo by LocationCertificates.signatureCryptoAlgo
    var signatureNonce by LocationCertificates.signatureNonce

    override fun toString(): String {
        val timeType = if (timestamp2 != 0) {
            "$timestamp1, $timestamp2"
        } else if (epochId != "" && timeValue != 0 && timeUnit != "") {
            "$epochId, $timeValue, $timeUnit"
        } else {
            "$timestamp1"
        }

        val signature = "$signatureValue $signatureCryptoAlgo $signatureNonce"

        return "LocationCertificate($verifierId, $claimId, $endorsementIds, $timeType, $evidenceType, $evidence, $signature)"
    }
}