package com.example.thecocktaildbapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecocktaildbapi.model.Cocktail
import com.example.thecocktaildbapi.repository.CocktailRepository
import kotlinx.coroutines.launch

class CocktailViewModel : ViewModel() {

    // Instanciamos el repositorio que ya creaste
    private val repository = CocktailRepository()

    // LiveData para manejar el estado de la lista de cócteles
    private val _cocktails = MutableLiveData<List<Cocktail>>()
    val cocktails: LiveData<List<Cocktail>> get() = _cocktails

    // LiveData para manejar errores (Puntos Extra en tu examen por manejo de red)
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // LiveData para el estado de carga (ProgressBar)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun searchCocktailsByName(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.searchByName(query)
                if (response.isSuccessful) {
                    // Si la API no encuentra resultados, devuelve un JSON con "drinks": null
                    val drinks = response.body()?.drinks ?: emptyList()
                    _cocktails.postValue(drinks)
                } else {
                    _errorMessage.postValue("Error en el servidor: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error de conexión. Verifica tu internet.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun filterCocktailsByIngredient(ingredient: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.filterByIngredient(ingredient)
                if (response.isSuccessful) {
                    val drinks = response.body()?.drinks ?: emptyList()
                    _cocktails.postValue(drinks)
                } else {
                    _errorMessage.postValue("Error en el servidor: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error de conexión. Verifica tu internet.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // LiveData para el cóctel seleccionado
    private val _cocktailDetail = MutableLiveData<Cocktail?>()
    val cocktailDetail: LiveData<Cocktail?> get() = _cocktailDetail

    fun fetchCocktailDetails(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getDetails(id)
                if (response.isSuccessful) {
                    // Tomamos el primer elemento de la lista "drinks"
                    _cocktailDetail.postValue(response.body()?.drinks?.firstOrNull())
                } else {
                    _errorMessage.postValue("Error en el servidor: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error de conexión. Verifica tu internet.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
