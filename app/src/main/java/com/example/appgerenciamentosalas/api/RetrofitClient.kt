package com.example.appgerenciamentosalas.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/"
    //private const val BASE_URL = "http://192.168.56.1/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val aulaApi: AulaApi by lazy {
        retrofit.create(AulaApi::class.java)
    }

    val salaApi: SalaApi by lazy {
        retrofit.create(SalaApi::class.java)
    }
}