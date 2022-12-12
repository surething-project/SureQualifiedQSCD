package main.kotlin.qscd.repositories

import main.kotlin.qscd.models.ProofsTypeEntity
import main.kotlin.qscd.models.ProofsTypes
import main.kotlin.qscd.responses.exceptions.proofType.ProofTypeAlreadyExistsException
import main.kotlin.qscd.responses.exceptions.proofType.ProofTypeNotFoundException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType

class ProofTypeRepository {

    fun registerProofType(proofType: ProofsType) = transaction {
        val proofTypeName = proofType.proofType.toString()

        try {
            getProofType(proofTypeName)
            throw ProofTypeAlreadyExistsException(proofTypeName)

        } catch (ex: ProofTypeNotFoundException) {
            ProofsTypeEntity.new {
                this.proofType = proofTypeName
                this.points = proofType.points
            }
        }
    }

    fun getProofType(name: String) = transaction {
        ProofsTypeEntity.find(ProofsTypes.proofType eq name).firstOrNull() ?:
            throw ProofTypeNotFoundException(name)
    }
}