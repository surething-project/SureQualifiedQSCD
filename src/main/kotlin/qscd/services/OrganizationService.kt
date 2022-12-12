package main.kotlin.qscd.services

import main.kotlin.qscd.models.OrganizationEntity
import main.kotlin.qscd.repositories.OrganizationRepository
import pt.ulisboa.tecnico.qscd.contract.OrganizationProto.Organization
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.Policy
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType.PROOF_TYPE

class OrganizationService(
    private val organizationRepository: OrganizationRepository,
    private val policyService: PolicyService
) {
    fun registerOrganization(organization: Organization) {
        val defaultPolicy = policyService.getDefaultPolicy()
        organizationRepository.addOrganization(organization, defaultPolicy)
    }

    fun getOrganization(organizationName: String): OrganizationEntity {
        return organizationRepository.getOrganization(organizationName)
    }

    fun getPolicy(organizationName: String): Policy {
        val proofsType = organizationRepository.getOrganizationPolicy(organizationName)

        val policyProto = Policy.newBuilder()
        for (proofType in proofsType) {
            val proofTypeEnum = PROOF_TYPE.valueOf(proofType.replace(",", ""))
            val proofTypeProto = ProofsType.newBuilder().setProofType(proofTypeEnum).build()
            policyProto.addProofsType(proofTypeProto)
        }

        return policyProto.build()
    }

    fun addPolicy(organizationName: String, policy: Policy) {
        val policyEntity = policyService.getPolicy(policy)
        organizationRepository.addPolicy(organizationName, policyEntity)
    }
}