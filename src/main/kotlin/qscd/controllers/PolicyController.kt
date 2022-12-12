package main.kotlin.qscd.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.kotlin.qscd.application.AddPolicies
import main.kotlin.qscd.responses.SuccessResponse
import main.kotlin.qscd.services.PolicyService
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.Policy

fun Route.policies(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    policyService: PolicyService
) {
    post<AddPolicies> {
        withContext(dispatcher) {
            val policy = Policy.parseFrom(call.receive<ByteArray>())
            policyService.registerPolicy(policy)
            call.respond(SuccessResponse(data = true))
        }
    }
}
