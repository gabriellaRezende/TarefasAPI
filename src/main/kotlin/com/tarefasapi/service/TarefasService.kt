package com.tarefasapi.com.tarefasapi.service

import com.tarefasapi.models.Tarefas
import org.jetbrains.exposed.sql.transactions.transaction

class TarefasService {
    fun listarTarefas(): List<String>{
        return transaction {
            listOf("Tarefa 1","Tarefa 2","Tarefa 3","Tarefa 4")
        }
    }

    fun criarTarefas(nome: String): String {
        return transaction {
            "Tarefa $nome criada com sucesso"
        }
    }
}