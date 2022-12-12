package main.kotlin.qscd.responses.exceptions

import io.ktor.http.*

abstract class DuplicateException(override val message: String) : QSCDException(message, HttpStatusCode.Conflict)

