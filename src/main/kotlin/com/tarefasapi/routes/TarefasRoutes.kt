package com.tarefasapi.com.tarefasapi.routes

import com.tarefasapi.com.tarefasapi.models.TarefasDTO
import com.tarefasapi.models.Tarefas
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.tarefasRoutes() {
    route("/tarefas") {
        // Listar todas as tarefas
        get {
            val tarefas = transaction {
                Tarefas.selectAll().map {
                    mapOf(
                        "id" to it[Tarefas.id],
                        "titulo" to it[Tarefas.titulo],
                        "descricao" to it[Tarefas.descricao],
                        "prioridade" to it[Tarefas.prioridade],
                        "status" to it[Tarefas.status],
                        "dataDeConclusao" to it[Tarefas.dataDeConclusao]?.toString()
                    )
                }
            }
            call.respond(tarefas)
        }

        // Criar uma nova tarefa
        post {
            val tarefaDTO = call.receive<TarefasDTO>()
            val tarefaId = transaction {
                Tarefas.insert {
                    it[titulo] = tarefaDTO.titulo
                    it[descricao] = tarefaDTO.descricao
                    it[prioridade] = tarefaDTO.prioridade
                    it[status] = tarefaDTO.status
                    it[dataDeConclusao] = null // Ajuste conforme necessário
                } get Tarefas.id
            }
            call.respondText("Tarefa criada com sucesso! ID: $tarefaId", status = HttpStatusCode.Created)
        }

        // Buscar uma tarefa pelo ID
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respondText("ID inválido!", status = HttpStatusCode.BadRequest)
                return@get
            }

            val tarefa = transaction {
                Tarefas.select { Tarefas.id eq id }.map {
                    mapOf(
                        "id" to it[Tarefas.id],
                        "titulo" to it[Tarefas.titulo],
                        "descricao" to it[Tarefas.descricao],
                        "prioridade" to it[Tarefas.prioridade],
                        "status" to it[Tarefas.status],
                        "dataDeConclusao" to it[Tarefas.dataDeConclusao]?.toString()
                    )
                }.firstOrNull()
            }

            if (tarefa == null) {
                call.respondText("Tarefa não encontrada!", status = HttpStatusCode.NotFound)
            } else {
                call.respond(tarefa)
            }
        }

        // Atualizar uma tarefa
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respondText("ID inválido!", status = HttpStatusCode.BadRequest)
                return@put
            }

            val tarefaDTO = call.receive<TarefasDTO>()

            val updatedRows = transaction {
                Tarefas.update({ Tarefas.id eq id }) {
                    it[titulo] = tarefaDTO.titulo
                    it[descricao] = tarefaDTO.descricao
                    it[prioridade] = tarefaDTO.prioridade
                    it[status] = tarefaDTO.status
                }
            }

            if (updatedRows == 0) {
                call.respondText("Tarefa não encontrada!", status = HttpStatusCode.NotFound)
            } else {
                call.respondText("Tarefa atualizada com sucesso!")
            }
        }

        // Remover uma tarefa
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respondText("ID inválido!", status = HttpStatusCode.BadRequest)
                return@delete
            }

            val deletedRows = transaction {
                Tarefas.deleteWhere { Tarefas.id eq id }
            }

            if (deletedRows == 0) {
                call.respondText("Tarefa não encontrada!", status = HttpStatusCode.NotFound)
            } else {
                call.respondText("Tarefa removida com sucesso!")
            }
        }
    }
}