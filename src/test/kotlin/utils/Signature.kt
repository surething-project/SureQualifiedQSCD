package test.kotlin.utils

import com.google.protobuf.ByteString
import eu.surething_project.core.Signature

fun createSignature(): Signature {
    return Signature.newBuilder().apply {
        this.value = ByteString.copyFromUtf8(DEFAULT_STRING)
        this.cryptoAlgo = DEFAULT_STRING
        this.nonce = DEFAULT_LONG
    }.build()
}