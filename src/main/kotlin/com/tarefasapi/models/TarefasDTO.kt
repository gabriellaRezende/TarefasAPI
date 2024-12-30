package com.tarefasapi.com.tarefasapi.models

import kotlinx.serialization.Serializable
import java.time.LocalDate

//DTO para criação de atualização de tarefas
@Serializable
data class TarefasDTO(
    val titulo: String,
    val descricao: String,
    val prioridade: Int,
    val status: String,
    val dataDeConclusao: LocalDate? = null,
)