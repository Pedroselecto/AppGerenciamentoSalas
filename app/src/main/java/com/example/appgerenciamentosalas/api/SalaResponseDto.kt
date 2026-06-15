package com.example.appgerenciamentosalas.api

data class SalaResponseDto(
    val id: Int,
    val andar: String,
    val numero: String,
    val tipo: String
)