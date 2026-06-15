package com.example.appgerenciamentosalas.api

data class AulaResponseDto(
    val id: Int,
    val disciplina: String,
    val turno: String,
    val curso: String,
    val diaDaSemana: String,
    val horarioInicio: String, // String é mais seguro para receber o formato HH:mm:ss do JSON
    val horarioFim: String,
    val professor: String,
    val localizacaoDaSala: String
)
