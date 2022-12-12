package main.kotlin.qscd.responses.exceptions

import io.ktor.http.*

abstract class QSCDException(override val message: String, val status: HttpStatusCode) : RuntimeException(message)