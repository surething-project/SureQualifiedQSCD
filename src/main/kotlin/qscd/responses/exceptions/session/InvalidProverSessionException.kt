package main.kotlin.qscd.responses.exceptions.session

import main.kotlin.qscd.responses.exceptions.NotFoundException

data class InvalidProverSessionException(val sessionId: String, val proverId: String) : NotFoundException("Prover with id '$proverId' does not belong to session with id '$sessionId'")