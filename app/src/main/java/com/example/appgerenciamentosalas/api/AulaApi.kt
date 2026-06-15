package com.example.appgerenciamentosalas.api

import retrofit2.Call
import retrofit2.http.*

interface AulaApi {

    @FormUrlEncoded
    @POST("aulas")
    fun cadastrarAula(
        @Field("nome") nome: String,
        @Field("turno") turno: String,
        @Field("curso") curso: String,
        @Field("horario_inicio") horarioInicio: String,
        @Field("horario_fim") horarioFim: String,
        @Field("professor") professor: String,
        @Field("dia") dia: String,
        @Field("sala_id") salaId: Int
    ): Call<Void>

    // 1. Endpoint para buscar as aulas do dia atual (usado na Home)
    @GET("aulas/filtrar")
    fun buscarAulasPorDia(@Query("dia") dia: String): Call<List<AulaResponseDto>>

    // 2. Endpoint para listar todas as aulas (usado na tela de Aulas)
    @GET("aulas")
    fun listarAulas(): Call<List<AulaResponseDto>>

    // 3. Endpoint para deletar a aula por ID
    @DELETE("aulas/{id}")
    fun deletarAula(@Path("id") id: Int): Call<Void>
}