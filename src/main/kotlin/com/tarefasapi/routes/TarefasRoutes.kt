package com.tarefasapi.com.tarefasapi.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.tarefasRoutes() {
    route("/tarefas") {
        get { call.respondText("Listando todas as tarefas") }
        post {
            val tarefas = call.receive<String>()
            call.respondText("Nova tarefa criada: {$tarefas}")
        }
    }
}