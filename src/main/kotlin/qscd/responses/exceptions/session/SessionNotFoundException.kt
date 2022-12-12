package main.kotlin.qscd.responses.exceptions.session

import main.kotlin.qscd.responses.exceptions.NotFoundException

data class SessionNotFoundException(val id: String) : NotFoundException("Session with id '$id' not found")

data class SessionNotFoundForOrganizationException(val id: String) : NotFoundException("Session not found for Organization with id '$id' not found")