package main.kotlin.qscd.responses.exceptions.policy

import main.kotlin.qscd.responses.exceptions.DuplicateException

data class PolicyAlreadyExistsException(val proofs: String) : DuplicateException("Policy with proofs '$proofs' already exists")