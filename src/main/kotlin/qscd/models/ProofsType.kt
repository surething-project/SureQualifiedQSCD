package main.kotlin.qscd.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// Table Object
object ProofsTypes : IntIdTable() {
    private const val VARCHAR_LENGTH = 256

    val proofType = varchar("proof_type", VARCHAR_LENGTH)
    val points = integer("points")
}

// Database Object DAO
class ProofsTypeEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ProofsTypeEntity>(ProofsTypes)

    var proofType by ProofsTypes.proofType
    var points by ProofsTypes.points

    override fun toString(): String {
        return "ProofsType($proofType, $points)"
    }
}