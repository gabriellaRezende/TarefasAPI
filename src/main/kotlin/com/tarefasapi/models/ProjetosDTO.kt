package com.tarefasapi.com.tarefasapi.models

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ProjetosDTO(
    val nome: String,
    val descricao: String?,
    val dataDeInicio: LocalDate?,
    val dataDeFim: LocalDate?
)