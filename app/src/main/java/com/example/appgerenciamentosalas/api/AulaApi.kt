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

    @GET("aulas/filtrar")
    fun buscarAulasPorDia(@Query("dia") dia: String): Call<List<AulaResponseDto>>

    @GET("aulas")
    fun listarAulas(): Call<List<AulaResponseDto>>

    @DELETE("aulas/{id}")
    fun deletarAula(@Path("id") id: Int): Call<Void>
}