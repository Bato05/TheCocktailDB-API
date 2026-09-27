package com.example.thecocktaildbapi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.thecocktaildbapi.databinding.FragmentDetailBinding
import com.example.thecocktaildbapi.model.Cocktail
import com.example.thecocktaildbapi.viewmodel.CocktailViewModel
import com.squareup.picasso.Picasso
import kotlin.getValue

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    /*
     Referencia al ViewModel compartido.
     Obtiene la información del elemento seleccionado
     sin necesidad de pasar argumentos (Bundles).
     */
    private val viewModel: CocktailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observar los datos del cóctel seleccionado
        /*
        Se suscribe al viewmodel
        Extrae el nombre y las Intrucciones del cocktail
        */
        viewModel.cocktailDetail.observe(viewLifecycleOwner) { cocktail ->
            if (cocktail != null) {
                binding.tvCocktailName.text = cocktail.strDrink
                binding.tvInstructions.text = cocktail.strInstructions
                binding.tvIngredientsList.text = formatIngredients(cocktail)
                Picasso.get().load(cocktail.strDrinkThumb).into(binding.ivCocktailImage)

                Toast.makeText(requireContext(), "Cargado: ${cocktail.strDrink}", Toast.LENGTH_SHORT).show()
            }
        }

        // Observa errores o error de estado de carga
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }
        }

        val cocktailId = arguments?.getString("COCKTAIL_ID")

        if (cocktailId != null) {
            // conectar al ViewModel para cargar los detalles
            viewModel.fetchCocktailDetails(cocktailId)
        }
    }

    private fun formatIngredients(cocktail: Cocktail): String {
        val ingredientsList = listOf(
            cocktail.strIngredient1 to cocktail.strMeasure1,
            cocktail.strIngredient2 to cocktail.strMeasure2,
            cocktail.strIngredient3 to cocktail.strMeasure3,
            cocktail.strIngredient4 to cocktail.strMeasure4,
            cocktail.strIngredient5 to cocktail.strMeasure5,
            cocktail.strIngredient6 to cocktail.strMeasure6,
            cocktail.strIngredient7 to cocktail.strMeasure7,
            cocktail.strIngredient8 to cocktail.strMeasure8,
            cocktail.strIngredient9 to cocktail.strMeasure9,
            cocktail.strIngredient10 to cocktail.strMeasure10,
            cocktail.strIngredient11 to cocktail.strMeasure11,
            cocktail.strIngredient12 to cocktail.strMeasure12,
            cocktail.strIngredient13 to cocktail.strMeasure13,
            cocktail.strIngredient14 to cocktail.strMeasure14,
            cocktail.strIngredient15 to cocktail.strMeasure15,
        )

        return ingredientsList
            .mapNotNull { (ingredient, measure) ->
                val trimmedIngredient = ingredient?.trim()
                val trimmedMeasure = measure?.trim()

                if (!trimmedIngredient.isNullOrEmpty()) {
                    if (!trimmedMeasure.isNullOrEmpty()) {
                        "• $trimmedMeasure $trimmedIngredient"
                    } else {
                        "• $trimmedIngredient"
                    }
                } else {
                    null
                }
            }
            .joinToString("\n")
    }

    //  Limpieza del binding para evitar fugas de memoria.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
