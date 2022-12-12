package main.kotlin.qscd.application

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource(homePath)
class Home
const val homePath = "/"

@Serializable
@Resource(createSessionPath)
class CreateSession
const val createSessionPath = "/session/create"

@Serializable
@Resource(joinSessionPath)
class JoinSession
const val joinSessionPath = "/session/{id}/join"

@Serializable
@Resource(finishSessionPath)
class FinishSession
const val finishSessionPath = "/session/finish"

@Serializable
@Resource(registerLocationCertificatePath)
class RegisterLocationCertificate
const val registerLocationCertificatePath = "/locationCertificate/register"

@Serializable
@Resource(registerOrganizationPath)
class RegisterOrganization
const val registerOrganizationPath = "/organization/register"

@Serializable
@Resource(addOrganizationPolicyPath)
class AddOrganizationPolicy
const val addOrganizationPolicyPath = "/organization/{organizationName}/policy"

@Serializable
@Resource(getOrganizationPolicyPath)
class GetOrganizationPolicy
const val getOrganizationPolicyPath = "/organization/{organizationName}/policy"

@Serializable
@Resource(getOrganizationSessionPath)
class GetOrganizationSession
const val getOrganizationSessionPath = "/organization/{organizationName}/session"

@Serializable
@Resource(addProofTypePath)
class AddProofType
const val addProofTypePath = "/proofType/add"

@Serializable
@Resource(addPoliciesPath)
class AddPolicies
const val addPoliciesPath = "/policies/add"

@Serializable
@Resource(registerBluetoothConnectionPath)
class RegisterBluetoothConnection
const val registerBluetoothConnectionPath = "/bluetooth/register/connection"

@Serializable
@Resource(registerBluetoothNearbyDevicesPath)
class RegisterBluetoothNearbyDevices
const val registerBluetoothNearbyDevicesPath = "/bluetooth/register/nearbyDevices"

@Serializable
@Resource(registerBluetoothNearbyDevicesNoAuthPath)
class RegisterBluetoothNearbyDevicesNoAuth
const val registerBluetoothNearbyDevicesNoAuthPath = "/bluetooth/register/nearbyDevices/{senderId}/noAuth"

@Serializable
@Resource(registerWiFiConnectionPath)
class RegisterWiFiConnection
const val registerWiFiConnectionPath = "/wifi/register/connection"

@Serializable
@Resource(registerWiFiNearbyDevicesPath)
class RegisterWiFiNearbyDevices
const val registerWiFiNearbyDevicesPath = "/wifi/register/nearbyDevices"

@Serializable
@Resource(registerWiFiNearbyDevicesNoAuthPath)
class RegisterWiFiNearbyDevicesNoAuth
const val registerWiFiNearbyDevicesNoAuthPath = "/wifi/register/nearbyDevices/{senderId}/noAuth"

@Serializable
@Resource(registerWiFiRotatingSSIDPath)
class RegisterWiFiRotatingSSID
const val registerWiFiRotatingSSIDPath = "/wifi/register/rotatingSSID"

@Serializable
@Resource(registerWiFiMultipleBeaconsPath)
class RegisterWiFiMultipleBeacons
const val registerWiFiMultipleBeaconsPath = "/wifi/register/multipleBeacons"

@Serializable
@Resource(registerSimpleCitizenCardPath)
class RegisterSimpleCitizenCard
const val registerSimpleCitizenCardPath = "/citizenCard/register/simple"

@Serializable
@Resource(registerSimpleCitizenCardNoAuthPath)
class RegisterSimpleCitizenCardNoAuth
const val registerSimpleCitizenCardNoAuthPath = "/citizenCard/register/simple/{senderId}/noAuth"

@Serializable
@Resource(registerSignedCitizenCardPath)
class RegisterSignedCitizenCard
const val registerSignedCitizenCardPath = "/citizenCard/register/signed"
