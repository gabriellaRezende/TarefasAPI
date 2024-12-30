package com.tarefasapi.com.tarefasapi.routes

import com.tarefasapi.com.tarefasapi.models.ProjetosDTO
import com.tarefasapi.models.Projetos
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.projetosRoutes() {
    route("/projetos") {

        //Listar todos os projetos
        get {
            val projetos = transaction {
                Projetos.selectAll().map {
                    mapOf(
                        "id" to it[Projetos.id],
                        "nome" to it[Projetos.nome],
                        "descricao" to it[Projetos.descricao],
                        "dataDeInicio" to it[Projetos.dataDeInicio]?.toString(),
                        "dataDeFim" to it[Projetos.dataDeFim]?.toString()
                    )
                }
            }
            call.respond(projetos)
        }

        //Criar um novo projeto
        post {
            val projetoDTO = call.receive<ProjetosDTO>()
            val projetoID = transaction {
                Projetos.insert {
                    it [nome] = projetoDTO.nome
                    it[descricao] = projetoDTO.descricao
                    it[dataDeInicio] = projetoDTO.dataDeInicio
                    it[dataDeFim] = projetoDTO.dataDeFim
                } get Projetos.id
            }
            call.respondText("Novo projeto foi criado com sucesso!! ID: $projetoID", status = HttpStatusCode.Created)
        }

        //Buscar projeto pelo ID
        get("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respondText ("ID Inválido", status = HttpStatusCode.BadRequest)
                return@get
            }

            val projetos = transaction {
                Projetos.select {Projetos.id.eq(id) }.map {
                    mapOf(
                        "id" to it[Projetos.id],
                        "nome" to it[Projetos.nome],
                        "descricao" to it[Projetos.descricao],
                        "dataDeInicio" to it[Projetos.dataDeInicio]?.toString(),
                        "dataDeFim" to it[Projetos.dataDeFim]?.toString(),
                    )
                }.firstOrNull()
            }

            if (projetos == null) {
                call.respondText("Projeto não encontrado!", status = HttpStatusCode.NotFound)
            } else {
                call.respond(projetos)
            }
        }

        //Atualizar um projeto
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respondText("ID Inválido", status = HttpStatusCode.BadRequest)
                return@put
            }

            val projetosDTO = call.receive<ProjetosDTO>()

            val updatedRows = transaction {
                Projetos.update({ Projetos.id.eq(id) }) {
                    it[nome] = projetosDTO.nome
                    it[descricao] = projetosDTO.descricao
                    it[dataDeInicio] = projetosDTO.dataDeInicio
                    it[dataDeFim] = projetosDTO.dataDeFim
                }
            }

            if (updatedRows == 0) {
                call.respondText("Projeto não encontrado!", status = HttpStatusCode.NotFound)
            } else {
                call.respondText("Projeto Atualizado com sucesso!")
            }
        }

        //Remover um projeto
        delete("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respondText("ID inválido!", status = HttpStatusCode.BadRequest)
                return@delete
            }

            val deletedRows = transaction {
                Projetos.deleteWhere { Projetos.id.eq(id) }
            }
            if (deletedRows == 0) {
                call.respondText("Projeto não encontrado!", status = HttpStatusCode.NotFound)
            } else {
                call.respondText("Projeto removido com sucesso!")
            }
        }
    }
}