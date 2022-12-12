package main.kotlin.qscd.responses.exceptions.session

import main.kotlin.qscd.responses.exceptions.NotFoundException

data class ProverNotFoundException(val id: String) : NotFoundException("Prover with id '$id' not found")