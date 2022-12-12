package main.kotlin.qscd.responses.exceptions.organization

import main.kotlin.qscd.responses.exceptions.NotFoundException

data class OrganizationNotFoundException(val name: String) : NotFoundException("Organization with name '$name' not found")