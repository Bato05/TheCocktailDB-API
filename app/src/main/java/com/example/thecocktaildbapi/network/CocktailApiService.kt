package com.example.thecocktaildbapi.network

import com.example.thecocktaildbapi.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    // Para buscar por nombre de cóctel (Ej: "Margarita")
    @GET("api/json/v1/1/search.php")
    suspend fun searchCocktailsByName(@Query("s") query: String): Response<CocktailResponse>

    // Para buscar por ingrediente (Ej: "Vodka")
    @GET("api/json/v1/1/filter.php")
    suspend fun filterCocktailsByIngredient(@Query("i") ingredient: String): Response<CocktailResponse>
    
    // Para buscar el detalle por ID (útil para la pantalla de detalle)
    @GET("api/json/v1/1/lookup.php")
    suspend fun getCocktailDetailsById(@Query("i") id: String): Response<CocktailResponse>
}
