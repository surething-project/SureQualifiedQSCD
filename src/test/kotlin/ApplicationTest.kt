package test.kotlin

import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import main.kotlin.qscd.application.*
import org.json.simple.JSONObject
import org.junit.Test
import test.kotlin.utils.*
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testServer() = testApplication {
        val response = client.get(homePath)
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testServerHTTPS() = testApplication {
        val response = client.get(homePath) { url { protocol = URLProtocol.HTTPS } }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testLocationCertificateRegister() = testApplication {
        val response = createPostRequest(client, registerLocationCertificatePath, createSLCT().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    // ================
    // = Policy Tests =
    // ================

    @Test
    fun testPolicyCreation() = testApplication {
        val response = createPostRequest(client, addPoliciesPath, createNewPolicy().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testAddPolicyOrganization() = testApplication {
        val policy = createNewPolicy().toByteArray()
        createPostRequest(client, addPoliciesPath, policy)

        val response = createPutRequest(client, addOrganizationPolicyPath, KIOSK_NAME, policy)
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testGetPolicyOrganization() = testApplication {
        val response = createGetRequest(client, getOrganizationPolicyPath, KIOSK_NAME, createPolicy().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    // ======================
    // = Organization Tests =
    // ======================

    @Test
    fun testOrganizationCreation() = testApplication {
        val response = createPostRequest(client, registerOrganizationPath, createNewOrganization().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testAddOrganizationPolicy() = testApplication {
        val response = createPutRequest(client, addOrganizationPolicyPath, KIOSK_NAME, createPolicy().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testGetOrganizationPolicy() = testApplication {
        val response = createGetRequest(client, getOrganizationPolicyPath, KIOSK_NAME, createPolicy().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    // =================
    // = Session Tests =
    // =================

    @Test
    fun testSessionCreation() = testApplication {
        val response = createPostRequest(client, createSessionPath, createOrganization().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testSessionJoin() = testApplication {
        val sessionData = getSessionData(client)
        val sessionId = sessionData["sessionId"].toString()
        val proverId = sessionData["proverId"].toString()

        val response = createPutRequest(client, joinSessionPath, sessionId, createProver(proverId).toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testSessionFinish() = testApplication {
        val token = getToken(client)

        val response = createAuthPutRequest(client, finishSessionPath, token, createOrganization().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    // ==============
    // = WiFi Tests =
    // ==============

    @Test
    fun testRegisterWiFiConnection() = testApplication {
        val token = getToken(client)
        val response = createAuthPostRequest(client, registerWiFiConnectionPath, token, createWiFiConnection().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testRegisterWiFiNearbyDevices() = testApplication {
        val token = getToken(client)
        val response = createAuthPostRequest(client, registerWiFiNearbyDevicesPath, token, createNearbyWiFiNetworks().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testRegisterWiFiNearbyDevicesNoAuth() = testApplication {
        val response = createPostRequest(client, registerWiFiNearbyDevicesNoAuthPath, DEFAULT_STRING, createNearbyWiFiNetworks().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testRegisterWiFiRotatingSSID() = testApplication {
        val token = getToken(client)
        val response = createAuthPostRequest(client, registerWiFiRotatingSSIDPath, token, createWiFiRotatingSSID().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testRegisterWiFiMultipleBeacons() = testApplication {
        val token = getToken(client)
        val response = createAuthPostRequest(client, registerWiFiMultipleBeaconsPath, token, createWiFiMultipleBeacons().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    // ===================
    // = Bluetooth Tests =
    // ===================

    @Test
    fun testRegisterBluetoothConnection() = testApplication {
        val token = getToken(client)
        val response = createAuthPostRequest(client, registerBluetoothConnectionPath, token, createBluetoothConnection().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testRegisterBluetoothNearbyDevices() = testApplication {
        val token = getToken(client)
        val response = createAuthPostRequest(client, registerBluetoothNearbyDevicesPath, token, createNearbyBluetoothDevices().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testRegisterBluetoothNearbyDevicesNoAuth() = testApplication {
        val response = createPostRequest(client, registerBluetoothNearbyDevicesNoAuthPath, DEFAULT_STRING, createNearbyBluetoothDevices().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    // ======================
    // = Citizen Card Tests =
    // ======================

    @Test
    fun testRegisterSimpleCitizenCard() = testApplication {
        val token = getToken(client)
        val response = createAuthPostRequest(client, registerSimpleCitizenCardPath, token, createCitizenCard().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testRegisterSimpleCitizenCardNoAuth() = testApplication {
        val response = createPostRequest(client, registerSimpleCitizenCardNoAuthPath, DEFAULT_STRING, createCitizenCard().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testRegisterSignedCitizenCard() = testApplication {
        val token = getToken(client)
        val response = createAuthPostRequest(client, registerSignedCitizenCardPath, token, createCitizenCardSigned().toByteArray())
        assertEquals(HttpStatusCode.OK, response.status)
    }
}

private suspend fun getSessionData(client: HttpClient): JSONObject {
    val response = createPostRequest(client, createSessionPath, createOrganization().toByteArray()).bodyAsText()
    val data = Gson().fromJson(response, JSONObject::class.java)["data"].toString()
    return Gson().fromJson(data, JSONObject::class.java)
}

private suspend fun getToken(client: HttpClient): String {
    val response = createPostRequest(client, createSessionPath, createOrganization().toByteArray()).bodyAsText()
    return Gson().fromJson(response, JSONObject::class.java)["token"].toString()
}

private suspend fun createGetRequest(client: HttpClient, url: String, parameter: String, body: ByteArray): HttpResponse {
    val parameterSlice = "{" + url.substringAfter("{").substringBefore("}") + "}"

    return client.get(url.replace(parameterSlice, parameter)) {
        url { protocol = URLProtocol.HTTPS }
        contentType(ContentType.Application.ProtoBuf)
        setBody(body)
    }
}

private suspend fun createPostRequest(client: HttpClient, url: String, body: ByteArray): HttpResponse {
    return client.post(url) {
        url { protocol = URLProtocol.HTTPS }
        contentType(ContentType.Application.ProtoBuf)
        setBody(body)
    }
}

private suspend fun createPostRequest(client: HttpClient, url: String, parameter: String, body: ByteArray): HttpResponse {
    val parameterSlice = "{" + url.substringAfter("{").substringBefore("}") + "}"

    return client.post(url.replace(parameterSlice, parameter)) {
        url { protocol = URLProtocol.HTTPS }
        contentType(ContentType.Application.ProtoBuf)
        setBody(body)
    }
}

private suspend fun createAuthPostRequest(client: HttpClient, url: String, token: String, body: ByteArray): HttpResponse {
    return client.post(url) {
        url { protocol = URLProtocol.HTTPS }
        contentType(ContentType.Application.ProtoBuf)
        bearerAuth(token)
        setBody(body)
    }
}

private suspend fun createAuthPutRequest(client: HttpClient, url: String, token: String, body: ByteArray): HttpResponse {
    return client.put(url) {
        url { protocol = URLProtocol.HTTPS }
        contentType(ContentType.Application.ProtoBuf)
        bearerAuth(token)
        setBody(body)
    }
}

private suspend fun createPutRequest(client: HttpClient, url: String, parameter: String, body: ByteArray): HttpResponse {
    val parameterSlice = "{" + url.substringAfter("{").substringBefore("}") + "}"

    return client.put(url.replace(parameterSlice, parameter)) {
        url { protocol = URLProtocol.HTTPS }
        contentType(ContentType.Application.ProtoBuf)
        setBody(body)
    }
}
