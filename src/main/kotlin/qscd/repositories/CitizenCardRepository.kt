package main.kotlin.qscd.repositories

import main.kotlin.qscd.models.SessionEntity
import main.kotlin.qscd.models.locationProofs.*
import org.jetbrains.exposed.sql.transactions.transaction
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.CitizenCard
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.CitizenCardSigned
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec


class CitizenCardRepository {
    fun registerCitizenCard(citizenCard: CitizenCard, senderId: String, sessionEntity: SessionEntity) = transaction {
        CitizenCardEntity.new {
            this.citizenCard = citizenCard.id
            this.senderId = senderId
            this.signature = citizenCard.signature.toByteArray()
            this.session = sessionEntity
            this.validated = false
        }
    }

    fun registerCitizenCardSigned(citizenCardSigned: CitizenCardSigned, senderId: String, sessionEntity: SessionEntity) = transaction {
        CitizenCardSignedEntity.new {
            this.citizenCard = citizenCardSigned.citizenCard.id
            this.citizenCardSignature = citizenCardSigned.signature.toByteArray()
            this.publicKey = citizenCardSigned.publicKey
            this.senderId = senderId
            this.signature = citizenCardSigned.citizenCard.signature.toByteArray()
            this.session = sessionEntity
            this.validated = false
        }
    }
}