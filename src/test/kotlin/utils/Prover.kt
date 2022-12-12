package test.kotlin.utils

import pt.ulisboa.tecnico.qscd.contract.ProverProto.Prover

fun createProver(proverId: String): Prover {
    return Prover.newBuilder().setId(proverId).build()
}
