package main.kotlin.qscd.responses.exceptions.proofType

import main.kotlin.qscd.responses.exceptions.DuplicateException

data class ProofTypeAlreadyExistsException(val name: String) : DuplicateException("Proof Type with name '$name' already exists")