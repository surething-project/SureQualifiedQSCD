package main.kotlin.qscd.responses.exceptions.policy

import main.kotlin.qscd.responses.exceptions.NotFoundException

data class PolicyNotFoundException(val proofs: String) : NotFoundException("Policy with proofs '$proofs' not found")