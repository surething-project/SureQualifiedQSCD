package main.kotlin.qscd.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

// Table Object
object Organizations : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val name = varchar("name", VARCHAR_LENGTH)
    val policyId = reference("policy_id", Policies, onDelete = ReferenceOption.CASCADE)
    val publicKey = binary("public_key")
    val privateKey = binary("private_key")
}

// Database Object DAO
class OrganizationEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<OrganizationEntity>(Organizations)

    var name by Organizations.name
    var policy by PolicyEntity referencedOn Organizations.policyId
    var publicKey by Organizations.publicKey
    var privateKey by Organizations.privateKey

    override fun toString(): String {
        return "Organization($name, $policy, $publicKey, $privateKey)"
    }
}