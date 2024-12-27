package com.tarefasapi

import com.tarefasapi.com.tarefasapi.routes.projetosRoutes
import com.tarefasapi.com.tarefasapi.routes.tarefasRoutes
import com.tarefasapi.models.Projetos
import com.tarefasapi.models.Tarefas
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    // Conectar ao banco de dados H2
    Database.connect(
        url = "jdbc:h2:./data/tarefasDB",
        driver = "org.h2.Driver",
        user = "sa",
        password = ""
    )

    // Criar tabelas no banco de dados
    transaction {
        SchemaUtils.create(Projetos, Tarefas)
    }

    println("Banco de dados H2 configurado e tabelas criadas!")

    // Iniciar o servidor Ktor
    embeddedServer(Netty, port = 8080) {
        routing {
            tarefasRoutes()
            projetosRoutes()
        }

    }.start(wait = true)

    println("Servidor Ktor rodando em http://localhost:8080")
}
