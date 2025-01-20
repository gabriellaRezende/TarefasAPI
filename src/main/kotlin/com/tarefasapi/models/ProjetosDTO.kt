package com.tarefasapi.com.tarefasapi.models

import com.tarefasapi.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ProjetosDTO(
    val id: Int,
    val nome: String,
    val descricao: String,
    @Serializable(with = LocalDateSerializer::class)
    val dataDeInicio: LocalDate?,
    @Serializable(with = LocalDateSerializer::class)
    val dataDeFim: LocalDate?
)