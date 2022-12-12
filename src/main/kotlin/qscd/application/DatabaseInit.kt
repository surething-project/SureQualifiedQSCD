package main.kotlin.qscd.application

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import main.kotlin.qscd.models.*
import main.kotlin.qscd.models.locationProofs.*
import main.kotlin.qscd.services.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import pt.ulisboa.tecnico.qscd.contract.OrganizationProto.Organization
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.Policy
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.ProofsType.PROOF_TYPE
import java.net.URI

const val HIKARI_CONFIG_KEY = "ktor.hikariconfig"
const val KIOSK_NAME = "kiosk"
const val INSPECTOR_NAME = "inspector"
const val BLUETOOTH_CONNECTION_POINTS = 10
const val QR_CODE_POINTS = 10
const val CITIZEN_CARD_POINTS = 15
const val CITIZEN_CARD_SIGNED_POINTS = 30
const val NEARBY_BLUETOOTH_POINTS = 15
const val NEARBY_WIFI_POINTS = 15

fun Application.initDatabase(
    sessionService: SessionService,
    policyService: PolicyService,
    organizationService: OrganizationService,
    proofTypeService: ProofTypeService
) {
    if (System.getenv("DATABASE_URL") != null) {
        val databaseURI = URI(System.getenv("DATABASE_URL"))
        val databaseUser = databaseURI.userInfo.split(":")[0]
        val databasePass = databaseURI.userInfo.split(":")[1]
        val databaseUrl = "jdbc:postgresql://" + databaseURI.host + ':' + databaseURI.port + databaseURI.path

        Database.connect(databaseUrl, user = databaseUser, password = databasePass)

    } else {
        val configPath = environment.config.property(HIKARI_CONFIG_KEY).getString()
        val dbConfig = HikariConfig(configPath)
        val dataSource = HikariDataSource(dbConfig)
        Database.connect(dataSource)
    }

    // When uncommented, resets tables for easier testing purposes
    createTables()
    populateTables(policyService, organizationService, proofTypeService, sessionService)
    LoggerFactory.getLogger(Application::class.simpleName).info("Database Initialized")
}

private fun createTables() = transaction {
    SchemaUtils.drop(
        LocationCertificates,
        Sessions,
        Organizations,
        ProofsTypes,
        Policies,
        BluetoothConnections,
        NearbyBluetoothDevicesTable,
        WiFiConnections,
        NearbyWiFiNetworksTable,
        RotatingSSIDsTable,
        WiFiMultipleBeacons,
        CitizenCards,
        CitizenCardsSigned
    )

    SchemaUtils.create(
        LocationCertificates,
        Sessions,
        Organizations,
        ProofsTypes,
        Policies,
        BluetoothConnections,
        NearbyBluetoothDevicesTable,
        WiFiConnections,
        NearbyWiFiNetworksTable,
        RotatingSSIDsTable,
        WiFiMultipleBeacons,
        CitizenCards,
        CitizenCardsSigned
    )
}

private fun createProofType(proofType: PROOF_TYPE, points: Int): ProofsType {
    return ProofsType.newBuilder()
        .setProofType(proofType)
        .setPoints(points)
        .build()
}

private fun createPolicy(proofsType: List<ProofsType>): Policy {
    val policy = Policy.newBuilder()
    for (proofType in proofsType) policy.addProofsType(proofType)
    return policy.build()
}

private fun populateTables(
    policyService: PolicyService,
    organizationService: OrganizationService,
    proofTypeService: ProofTypeService,
    sessionService: SessionService
) = transaction {
    // Default Policy
    val defaultPolicy = Policy.newBuilder().build()
    policyService.registerPolicy(defaultPolicy)

    // Create Proof Types
    val bluetoothConnection = createProofType(PROOF_TYPE.BLUETOOTH_CONNECTION, BLUETOOTH_CONNECTION_POINTS)
    proofTypeService.registerProofType(bluetoothConnection)

    val qrCode = createProofType(PROOF_TYPE.QRCODE_SCAN, QR_CODE_POINTS)
    proofTypeService.registerProofType(qrCode)

    val nearbyBluetoothDevices = createProofType(PROOF_TYPE.BLUETOOTH_NEARBY_DEVICES, NEARBY_BLUETOOTH_POINTS)
    proofTypeService.registerProofType(nearbyBluetoothDevices)

    val nearbyWiFiNetworks = createProofType(PROOF_TYPE.WIFI_NEARBY_DEVICES, NEARBY_WIFI_POINTS)
    proofTypeService.registerProofType(nearbyWiFiNetworks)

    val citizenCard = createProofType(PROOF_TYPE.CITIZEN_CARD, CITIZEN_CARD_POINTS)
    proofTypeService.registerProofType(citizenCard)

    val citizenCardSigned = createProofType(PROOF_TYPE.CITIZEN_CARD_SIGNED, CITIZEN_CARD_SIGNED_POINTS)
    proofTypeService.registerProofType(citizenCardSigned)

    // Create Policies
    val kioskPolicy = createPolicy(listOf(qrCode, nearbyWiFiNetworks, nearbyBluetoothDevices, citizenCard, citizenCardSigned))
    policyService.registerPolicy(kioskPolicy)

    val inspectorPolicy = createPolicy(listOf(bluetoothConnection, citizenCardSigned))
    policyService.registerPolicy(inspectorPolicy)

    // Create Organizations
    val kiosk = Organization.newBuilder().setName(KIOSK_NAME).build()
    organizationService.registerOrganization(kiosk)
    
    val inspector = Organization.newBuilder().setName(INSPECTOR_NAME).build()
    organizationService.registerOrganization(inspector)

    // Add Policy to Organizations
    organizationService.addPolicy(KIOSK_NAME, kioskPolicy)
    organizationService.addPolicy(INSPECTOR_NAME, inspectorPolicy)

    // Create Default Session
    sessionService.createDefaultSession()

    // Create Default Finished Session
    sessionService.createDefaultFinishedSession()
}
