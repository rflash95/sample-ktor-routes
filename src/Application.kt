package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        trace { application.log.trace(it.buildText()) } //debug

        route("/weather") {
            //shortcut function
            get("usa") {
                call.respondText { "The weather is US: snow" }
            }

            //builder function
            route("/europe", HttpMethod.Get) {
                header("systemtoken", "weathersystem") {
                    param("name") {
                        handle {
                            var name = call.parameters.get("name") //get param by key
                            call.respondText { "Dear $name, The Weather in Europe: sunny" }
                        }
                    }
                    handle {
                        call.respondText { "The weather in Europe : sunny" }
                    }
                }
            }
        }
    }
}

