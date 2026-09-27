package com.example.thecocktaildbapi.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecocktaildbapi.adapter.CocktailAdapter
import com.example.thecocktaildbapi.databinding.ActivityMainBinding
import com.example.thecocktaildbapi.viewmodel.CocktailViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CocktailViewModel
    private lateinit var adapter: CocktailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Configurar ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Inicializar ViewModel
        viewModel = ViewModelProvider(this)[CocktailViewModel::class.java]

        // 3. Configurar RecyclerView y Adapter
        setupRecyclerView()

        // 4. Observar los LiveData del ViewModel
        observeViewModel()

        // 5. Configurar el buscador
        setupSearch()
    }

    private fun setupRecyclerView() {
        adapter = CocktailAdapter(emptyList()) { cocktail ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("COCKTAIL_ID", cocktail.idDrink)
            }
            startActivity(intent)
        }
        binding.recyclerViewCocktails.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCocktails.adapter = adapter
    }

    private fun observeViewModel() {
        // Observar la lista de cócteles
        viewModel.cocktails.observe(this) { cocktails ->
            adapter.updateData(cocktails)
        }

        // Observar el estado de carga para mostrar/ocultar el ProgressBar
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observar mensajes de error
        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupSearch() {
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            val query = v.text.toString().trim()
            if (query.isNotEmpty()) {
                if (binding.rbByName.isChecked) {
                    viewModel.searchCocktailsByName(query)
                } else {
                    viewModel.filterCocktailsByIngredient(query)
                }
            }
            true
        }
    }
}
