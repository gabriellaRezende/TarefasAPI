package com.tarefasapi.com.tarefasapi.service

import com.tarefasapi.models.Tarefas
import org.jetbrains.exposed.sql.transactions.transaction

class ProjetosService {
    fun listarProjetos(): List<String>{
        return transaction {
            listOf("Projeto 1","Projeto 2","Projeto 3", "Projeto 4")
        }
    }

    fun criarProjeto(nome: String): String {
        return transaction {
            "Projeto $nome criado com sucesso"
        }
    }
}