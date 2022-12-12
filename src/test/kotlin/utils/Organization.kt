package test.kotlin.utils

import main.kotlin.qscd.application.KIOSK_NAME
import pt.ulisboa.tecnico.qscd.contract.OrganizationProto.Organization

fun createOrganization(): Organization {
    return Organization.newBuilder()
        .setName(KIOSK_NAME)
        .build()
}

fun createNewOrganization(): Organization {
    return Organization.newBuilder()
        .setName(DEFAULT_STRING)
        .build()
}
