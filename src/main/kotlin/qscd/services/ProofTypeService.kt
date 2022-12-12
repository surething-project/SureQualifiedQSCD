package main.kotlin.qscd.services

import main.kotlin.qscd.repositories.ProofTypeRepository
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType

class ProofTypeService(
    private val proofTypeRepository: ProofTypeRepository
) {
    fun registerProofType(proofsType: ProofsType) {
        proofTypeRepository.registerProofType(proofsType)
    }
}