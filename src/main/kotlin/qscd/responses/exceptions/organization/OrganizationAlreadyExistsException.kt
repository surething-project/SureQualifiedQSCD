package main.kotlin.qscd.responses.exceptions.organization

import main.kotlin.qscd.responses.exceptions.DuplicateException

data class OrganizationAlreadyExistsException(val name: String) : DuplicateException("Organization with name '$name' already exists")