package main.kotlin.qscd.controllers

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import kotlinx.html.*
import main.kotlin.qscd.application.Home

fun Route.home() {
    get<Home> {
        call.respondHtml {
            head {
                meta(charset = "UTF-8")
                title { +"QSCD" }
            }
            body {
                h1 {
                    +"Qualified Signature Creation Device | Coming Soon ..."
                }
            }
        }
    }
}