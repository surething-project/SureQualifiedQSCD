package main.kotlin.qscd.repositories

import main.kotlin.qscd.models.OrganizationEntity
import main.kotlin.qscd.models.Organizations
import main.kotlin.qscd.models.PolicyEntity
import main.kotlin.qscd.responses.exceptions.organization.OrganizationAlreadyExistsException
import main.kotlin.qscd.responses.exceptions.organization.OrganizationNotFoundException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import pt.ulisboa.tecnico.qscd.contract.OrganizationProto.Organization
import java.security.KeyPairGenerator

class OrganizationRepository {

    fun addOrganization(organization: Organization, defaultPolicy: PolicyEntity) = transaction {
        if (!OrganizationEntity.find(Organizations.name eq organization.name).empty()) {
            throw OrganizationAlreadyExistsException(organization.name)
        }

        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()
        
        OrganizationEntity.new {
            this.name = organization.name
            this.policy = defaultPolicy
            this.publicKey = keyPair.public.encoded
            this.privateKey = keyPair.private.encoded
        }
    }

    fun getOrganization(name: String) = transaction {
        OrganizationEntity.find(Organizations.name eq name).firstOrNull() ?:
            throw OrganizationNotFoundException(name)
    }

    fun addPolicy(name: String, policy: PolicyEntity) = transaction {
        val organization = getOrganization(name)
        organization.policy = policy
    }

    fun getOrganizationPolicy(name: String) = transaction {
        val organization = getOrganization(name)
        organization.policy.proofsType.split(" ")
    }
}