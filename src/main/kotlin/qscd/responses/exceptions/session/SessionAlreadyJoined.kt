package main.kotlin.qscd.responses.exceptions.session

import main.kotlin.qscd.responses.exceptions.DuplicateException

data class SessionAlreadyJoined(val id: String) : DuplicateException("Session with id '$id' was already joined")