package com.tarefasapi

import com.tarefasapi.com.tarefasapi.routes.projetosRoutes
import com.tarefasapi.com.tarefasapi.routes.tarefasRoutes
import com.tarefasapi.models.Projetos
import com.tarefasapi.models.Tarefas
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.h2.tools.Server
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction



fun startH2Console() {
    try {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start()
        println("H2 Console disponível em http://localhost:8082")
    } catch (e: Exception) {
        println("Error ao iniciar o H2 Console: ${e.message}")
    }
}

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

    startH2Console()

    // Iniciar o servidor Ktor
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json(
                Json{
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                allowStructuredMapKeys = true
            })
        }
        routing {
            tarefasRoutes()
            projetosRoutes()
        }

    }.start(wait = true)

    println("Servidor Ktor rodando em http://localhost:8080")
}
