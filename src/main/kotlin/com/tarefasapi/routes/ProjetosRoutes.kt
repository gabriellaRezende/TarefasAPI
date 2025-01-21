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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Route.projetosRoutes() {
    route("/projetos") {

        //Listar todos os projetos
        get {
            try {
                val projetos = transaction {
                    Projetos.selectAll().map {
                        ProjetosDTO(
                            id = it[Projetos.id],
                            nome = it[Projetos.nome],
                            descricao = it[Projetos.descricao]?: "",
                            dataDeInicio = it[Projetos.dataDeInicio],
                            dataDeFim = it[Projetos.dataDeFim]
                        )
                    }
                }

                println("Projetos encontrados: $projetos")
                call.respond(projetos)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError, "Erro ao listar os projetos")
            }
        }

        //Criar um novo projeto
        post {
            val projetoDTO = call.receive<ProjetosDTO>()
            val projetoID = transaction {
                Projetos.insert {
                    it[nome] = projetoDTO.nome
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
                    ProjetosDTO(
                        id = it[Projetos.id],
                        nome= it[Projetos.nome],
                        descricao = it[Projetos.descricao]?: "",
                        dataDeInicio = it[Projetos.dataDeInicio],
                        dataDeFim = it[Projetos.dataDeFim],
                    )
                }.firstOrNull()
            }

            if (projetos == null) {
                call.respondText("Projeto não existe!", status = HttpStatusCode.NotFound)
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

            //Recebe os dados do projeto
            val projetosDTO = call.receive<ProjetosDTO>()

            //Valida os campos
            val nome = projetosDTO.nome
            val descricao = projetosDTO.descricao
            val dataDeInicio = projetosDTO.dataDeInicio
            val dataDeFim = projetosDTO.dataDeFim

            if (nome.isNullOrBlank() || descricao.isNullOrBlank() || dataDeInicio == null || dataDeFim == null) {
                call.respondText("Dados Inválidos", status = HttpStatusCode.BadRequest)
                return@put
            }

            //Converter data string para LocalDate
            val dataDeInicioParsed = try {
                java.sql.Date.valueOf(dataDeInicio, )
            } catch (e: Exception) {
                call.respondText("Formato de data inválido para dataDeInicio", status = HttpStatusCode.BadRequest)
                return@put
            }

            val dataDeFimParsed = try {
                java.sql.Date.valueOf(dataDeFim)
            } catch (e: Exception) {
                call.respondText("Formato de data inválido para dataDeFim", status = HttpStatusCode.BadRequest)
                return@put
            }

            //Atualizar o projeto no banco
            val updatedRows = transaction {
                Projetos.update({ Projetos.id.eq(id) }) {
                    it[nome] = nome
                    it[descricao] = descricao // Usando a variável 'descricao' validada
                    it[dataDeInicio] = dataDeInicioParsed // Usando a variável 'dataDeInicioParsed' convertida para java.sql.Date
                    it[dataDeFim] = dataDeFimParsed
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