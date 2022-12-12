package main.kotlin.qscd.controllers

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.kotlin.qscd.application.AddOrganizationPolicy
import main.kotlin.qscd.application.GetOrganizationPolicy
import main.kotlin.qscd.application.RegisterOrganization
import main.kotlin.qscd.responses.SuccessResponse
import main.kotlin.qscd.services.OrganizationService
import pt.ulisboa.tecnico.qscd.contract.OrganizationProto.Organization
import pt.ulisboa.tecnico.qscd.contract.PolicyProto.Policy

fun Route.organizations(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    organizationService: OrganizationService
) {
    post<RegisterOrganization> {
        withContext(dispatcher) {
            val organization = Organization.parseFrom(call.receive<ByteArray>())
            organizationService.registerOrganization(organization)
            call.respond(SuccessResponse(data = true))
        }
    }

    get<GetOrganizationPolicy> {
        withContext(dispatcher) {
            val organizationName = call.parameters["organizationName"]
            val policies = organizationService.getPolicy(organizationName!!)
            call.respond(SuccessResponse(data = policies))
        }
    }

    put<AddOrganizationPolicy> {
        withContext(dispatcher) {
            val organizationName = call.parameters["organizationName"]
            val policy = Policy.parseFrom(call.receive<ByteArray>())

            organizationService.addPolicy(organizationName!!, policy)
            call.respond(SuccessResponse(data = true))
        }
    }
}
