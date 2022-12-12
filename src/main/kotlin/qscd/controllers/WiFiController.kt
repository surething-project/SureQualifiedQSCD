package main.kotlin.qscd.controllers

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.kotlin.qscd.application.*
import main.kotlin.qscd.responses.SuccessResponse
import main.kotlin.qscd.services.WiFiService
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.MeanTimeToRespond
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.MultipleBeacons
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.NearbyWiFiNetworks
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.RotatingSSID

fun Route.wifis(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    wiFiService: WiFiService
) {

    post<RegisterWiFiNearbyDevicesNoAuth> {
        withContext(dispatcher) {
            val nearbyWiFiNetworks = NearbyWiFiNetworks.parseFrom(call.receive<ByteArray>())
            val senderId = call.parameters["senderId"] ?: throw NotFoundException("Sender Id is necessary")

            wiFiService.registerNearbyWiFiNetworks(nearbyWiFiNetworks, senderId)
            call.respond(SuccessResponse(data = true))
        }
    }

    authenticate("token-auth") {
        post<RegisterWiFiConnection> {
            withContext(dispatcher) {
                val wiFiConnection = MeanTimeToRespond.parseFrom(call.receive<ByteArray>())
                val senderAndSessionData = getSenderAndSessionIds(call)

                wiFiService.registerWiFiConnection(wiFiConnection, senderAndSessionData)
                call.respond(SuccessResponse(data = true))
            }
        }

        post<RegisterWiFiNearbyDevices> {
            withContext(dispatcher) {
                val nearbyWiFiNetworks = NearbyWiFiNetworks.parseFrom(call.receive<ByteArray>())
                val senderAndSessionData = getSenderAndSessionIds(call)

                wiFiService.registerNearbyWiFiNetworks(nearbyWiFiNetworks, senderAndSessionData)
                call.respond(SuccessResponse(data = true))
            }
        }

        post<RegisterWiFiRotatingSSID> {
            withContext(dispatcher) {
                val rotatingSSID = RotatingSSID.parseFrom(call.receive<ByteArray>())
                val senderAndSessionData = getSenderAndSessionIds(call)

                wiFiService.registerWiFiRotatingSSID(rotatingSSID, senderAndSessionData)
                call.respond(SuccessResponse(data = true))
            }
        }

        post<RegisterWiFiMultipleBeacons> {
            withContext(dispatcher) {
                val wiFiMultipleBeacons = MultipleBeacons.parseFrom(call.receive<ByteArray>())
                val senderAndSessionData = getSenderAndSessionIds(call)

                wiFiService.registerWiFiMultipleBeacons(wiFiMultipleBeacons, senderAndSessionData)
                call.respond(SuccessResponse(data = true))
            }
        }
    }
}