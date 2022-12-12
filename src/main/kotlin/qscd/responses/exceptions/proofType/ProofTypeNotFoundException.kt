package main.kotlin.qscd.responses.exceptions.proofType

import main.kotlin.qscd.responses.exceptions.NotFoundException

data class ProofTypeNotFoundException(val name: String) : NotFoundException("Proof Type with name '$name' not found")