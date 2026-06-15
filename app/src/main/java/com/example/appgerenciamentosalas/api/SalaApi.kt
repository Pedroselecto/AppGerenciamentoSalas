package com.example.appgerenciamentosalas.api

import retrofit2.Call
import retrofit2.http.GET

interface SalaApi {
    @GET("salas")
    fun listarSalas(): Call<List<SalaResponseDto>>
}