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
import main.kotlin.qscd.application.RegisterBluetoothConnection
import main.kotlin.qscd.application.RegisterBluetoothNearbyDevices
import main.kotlin.qscd.application.RegisterBluetoothNearbyDevicesNoAuth
import main.kotlin.qscd.responses.SuccessResponse
import main.kotlin.qscd.services.BluetoothService
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.BluetoothConnection
import pt.ulisboa.tecnico.qscd.contract.LocationProofsProto.NearbyBluetoothDevices

fun Route.bluetooths(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    bluetoothService: BluetoothService
) {

    post<RegisterBluetoothNearbyDevicesNoAuth> {
        withContext(dispatcher) {
            val nearbyBluetoothDevices = NearbyBluetoothDevices.parseFrom(call.receive<ByteArray>())
            val senderId = call.parameters["senderId"] ?: throw NotFoundException("Sender Id is necessary")

            bluetoothService.registerNearbyBluetoothDevices(nearbyBluetoothDevices, senderId)
            call.respond(SuccessResponse(data = true))
        }
    }

    authenticate("token-auth") {
        post<RegisterBluetoothConnection> {
            withContext(dispatcher) {
                val bluetoothConnection = BluetoothConnection.parseFrom(call.receive<ByteArray>())
                val senderAndSessionData = getSenderAndSessionIds(call)
                bluetoothService.registerBluetoothConnection(bluetoothConnection, senderAndSessionData)
                call.respond(SuccessResponse(data = true))
            }
        }

        post<RegisterBluetoothNearbyDevices> {
            withContext(dispatcher) {
                val nearbyBluetoothDevices = NearbyBluetoothDevices.parseFrom(call.receive<ByteArray>())
                val senderAndSessionData = getSenderAndSessionIds(call)
                bluetoothService.registerNearbyBluetoothDevices(nearbyBluetoothDevices, senderAndSessionData)
                call.respond(SuccessResponse(data = true))
            }
        }
    }
}