package main.kotlin.qscd.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// Table Object
object Policies : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val proofsType = varchar("proofs_type", VARCHAR_LENGTH)
    val points = integer("points")
}

// Database Object DAO
class PolicyEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<PolicyEntity>(Policies)

    var proofsType by Policies.proofsType
    var points by Policies.points

    override fun toString(): String {
        return "Policy($proofsType, $points)"
    }
}