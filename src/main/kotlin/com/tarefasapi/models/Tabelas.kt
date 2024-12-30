package com.tarefasapi.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

// Tabela de Projetos
object Projetos : Table("projetos") {
    val id = integer("id").autoIncrement()
    val nome = varchar("nome", 255)
    val descricao = text("descricao").nullable()
    val dataDeInicio = date("data_de_inicio").nullable() // Tipo de data
    val dataDeFim = date("data_de_fim").nullable() // Tipo de data
    override val primaryKey = PrimaryKey(id)
}

// Tabela de Tarefas
object Tarefas : Table("tarefas") {
    val id = integer("id").autoIncrement()
    val titulo = varchar("titulo", 255)
    val descricao = varchar("descricao", 255).nullable()
    val status = varchar("status", 50)
    val prioridade = integer("prioridade")
    val dataDeConclusao = date("data_de_conclusao").nullable()
    val projetoId = integer("projeto_id").references(Projetos.id)
    override val primaryKey = PrimaryKey(id)
}
