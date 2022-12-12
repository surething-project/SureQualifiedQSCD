package main.kotlin.qscd.repositories

import main.kotlin.qscd.models.Policies
import main.kotlin.qscd.models.PolicyEntity
import main.kotlin.qscd.models.ProofsTypeEntity
import main.kotlin.qscd.responses.exceptions.policy.PolicyAlreadyExistsException
import main.kotlin.qscd.responses.exceptions.policy.PolicyNotFoundException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PolicyRepository {

    fun registerPolicy(proofsType: List<ProofsTypeEntity>) = transaction {
        val proofsTypeString = proofsType.joinToString { it.proofType }

        try {
            getPolicy(proofsType)
            throw PolicyAlreadyExistsException(proofsTypeString)

        } catch (ex: PolicyNotFoundException) {
            var points = 0
            for (i in proofsType.indices) {
                points += proofsType[i].points
            }

            PolicyEntity.new {
                this.proofsType = proofsTypeString
                this.points = points
            }
        }
    }

    fun getPolicy(proofsType: List<ProofsTypeEntity>) = transaction {
        val proofsTypeString = proofsType.joinToString { it.proofType }
        PolicyEntity.find(Policies.proofsType eq proofsTypeString).firstOrNull() ?:
            throw PolicyNotFoundException(proofsTypeString)
    }

    fun getDefaultPolicy() = transaction {
        PolicyEntity.find(Policies.proofsType eq "").first()
    }
}