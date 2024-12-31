package com.tarefasapi.com.tarefasapi.models

import com.tarefasapi.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

//DTO para criação de atualização de tarefas
@Serializable
data class TarefasDTO(
    val projetoId: Int,
    val titulo: String,
    val descricao: String? = null,
    val prioridade: Int,
    val status: String,
    @Serializable(with = LocalDateSerializer::class)
    val dataDeConclusao: LocalDate? = null,
)