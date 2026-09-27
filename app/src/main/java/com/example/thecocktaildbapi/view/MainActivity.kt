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
        // 1. Observar la lista de ingredientes y poblar el Spinner
        viewModel.ingredientsList.observe(this) { ingredients ->
            val spinnerAdapter = android.widget.ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                ingredients
            )
            binding.spinnerIngredients.adapter = spinnerAdapter
        }

        // 2. Escuchar la selección del Spinner
        binding.spinnerIngredients.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedIngredient = parent.getItemAtPosition(position).toString()

                // Evitar buscar cuando se selecciona el texto por defecto
                if (selectedIngredient != "Selecciona un ingrediente") {
                    viewModel.filterCocktailsByIngredient(selectedIngredient)
                }
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        // 3. Mantener la búsqueda por nombre en la barra de texto
        binding.etSearch.setOnEditorActionListener { v, _, _ ->
            val query = v.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchCocktailsByName(query)
                // Reiniciar el spinner a la posición 0 ("Selecciona un ingrediente") para evitar que siga filtrando
                binding.spinnerIngredients.setSelection(0)
            }
            true
        }
    }
}
