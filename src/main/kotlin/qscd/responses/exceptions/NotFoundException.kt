package main.kotlin.qscd.responses.exceptions

import io.ktor.http.*

abstract class NotFoundException(override val message: String) : QSCDException(message, HttpStatusCode.NotFound)

