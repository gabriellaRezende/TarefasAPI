package com.tarefasapi.com.tarefasapi.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.projetosRoutes() {
    route("/projetos") {
        get { call.respondText("Listando todos os projetos") }
        post {
            val projeto = call.receive<String>()
            call.respondText("Novo projeto foi criado: {$projeto}")
        }
    }
}