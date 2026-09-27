package com.example.thecocktaildbapi.repository

import com.example.thecocktaildbapi.model.CocktailResponse
import com.example.thecocktaildbapi.network.RetrofitClient
import retrofit2.Response

class CocktailRepository {
    private val api = RetrofitClient.apiService

    // buscar la bebida por su nombre.
    suspend fun searchByName(query: String): Response<CocktailResponse> {
        return api.searchCocktailsByName(query)
    }

    // buscar por ingrediente
    suspend fun filterByIngredient(ingredient: String): Response<CocktailResponse> {
        return api.filterCocktailsByIngredient(ingredient)
    }

    // Obtener detalles de la bebida seleccionada mediante su ID único
    suspend fun getDetails(id: String): Response<CocktailResponse> {
        return api.getCocktailDetailsById(id)
    }

    suspend fun getAllIngredients(): Response<CocktailResponse> {
        return api.getAllIngredients()
    }
}
