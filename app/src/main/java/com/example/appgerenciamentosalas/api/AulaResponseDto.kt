package com.example.appgerenciamentosalas.api

data class AulaResponseDto(
    val id: Int,
    val disciplina: String,
    val turno: String,
    val curso: String,
    val diaDaSemana: String,
    val horarioInicio: String,
    val horarioFim: String,
    val professor: String,
    val localizacaoDaSala: String
)
