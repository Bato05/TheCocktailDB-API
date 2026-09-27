package com.example.thecocktaildbapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecocktaildbapi.R
import com.example.thecocktaildbapi.databinding.ItemCocktailBinding
import com.example.thecocktaildbapi.model.Cocktail
import com.squareup.picasso.Picasso

class CocktailAdapter(
    private var cocktailList: List<Cocktail>,
    private val onItemClick: (Cocktail) -> Unit
) : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    class CocktailViewHolder(val binding: ItemCocktailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        // Inflamos la vista utilizando la función inflate del Binding
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktail = cocktailList[position]

        holder.binding.tvCocktailName.text = cocktail.strDrink

        Picasso.get()
            .load(cocktail.strDrinkThumb) // Busca/descargar la Imagen del Cocktail
            .placeholder(R.mipmap.ic_launcher) // Mientras espera, Imagen de carga
            .error(R.mipmap.ic_launcher_round) // En caso de un error, Imagen de error
            .into(holder.binding.ivCocktailImage) // Setea la Imagen del Cocktail

        // Al seleccionar un Cocktail, se capturan los datos de dicho Cocktail para el detalle
        holder.itemView.setOnClickListener {
            onItemClick(cocktail)
        }
    }

    override fun getItemCount(): Int = cocktailList.size

    // Función para actualizar la lista
    fun updateData(newList: List<Cocktail>) {
        cocktailList = newList
        notifyDataSetChanged()
    }
}