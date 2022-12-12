package main.kotlin.qscd.services

import main.kotlin.qscd.models.PolicyEntity
import main.kotlin.qscd.models.ProofsTypeEntity
import main.kotlin.qscd.repositories.PolicyRepository
import main.kotlin.qscd.repositories.ProofTypeRepository
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.Policy
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType

class PolicyService(
    private val policyRepository: PolicyRepository,
    private val proofsTypeRepository: ProofTypeRepository
) {
    private fun getProofsType(proofsTypeList: List<ProofsType>): List<ProofsTypeEntity> {
        val proofsType = mutableListOf<ProofsTypeEntity>()
        for (i in proofsTypeList.indices) {
            val proofType = proofsTypeRepository.getProofType(proofsTypeList[i].proofType.toString())
            proofsType.add(proofType)
        }

        proofsType.sortBy { it.proofType }
        return proofsType
    }

    fun registerPolicy(policy: Policy) {
        val proofsType = getProofsType(policy.proofsTypeList)
        policyRepository.registerPolicy(proofsType)
    }

    fun getPolicy(policy: Policy): PolicyEntity {
        val proofsType = getProofsType(policy.proofsTypeList)
        return policyRepository.getPolicy(proofsType)
    }

    fun getDefaultPolicy(): PolicyEntity {
        return policyRepository.getDefaultPolicy()
    }
}