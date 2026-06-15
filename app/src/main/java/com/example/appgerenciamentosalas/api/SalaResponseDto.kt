package com.example.appgerenciamentosalas.api

// Os nomes das variáveis precisam ser idênticos aos do record no Java
data class SalaResponseDto(
    val id: Int,
    val andar: String,
    val numero: String,
    val tipo: String
)