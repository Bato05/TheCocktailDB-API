package com.example.thecocktaildbapi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.thecocktaildb.com/"

    val apiService: CocktailApiService by lazy {
        Retrofit.Builder() // para construir el objeto
            .baseUrl(BASE_URL) // Indicamos que las peticiones partes de esta URL: BASE_URL
            .addConverterFactory(GsonConverterFactory.create()) // Cuando Recibe JSON, utilizá Gson para convertirlo en objetos en Kotlin
            .build() // construye el objeto con esta conf
            .create(CocktailApiService::class.java) // Crea una implementación de CocktailApiService
    }
}
