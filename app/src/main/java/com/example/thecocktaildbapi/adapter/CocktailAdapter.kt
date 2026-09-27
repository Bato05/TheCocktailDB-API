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

    // 1. El ViewHolder ahora recibe el Binding generado en lugar de un View crudo
    class CocktailViewHolder(val binding: ItemCocktailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        // 2. Inflamos la vista utilizando el método inflate del Binding
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktail = cocktailList[position]

        // 3. Accedemos a las vistas directamente a través de la propiedad 'binding'
        holder.binding.tvCocktailName.text = cocktail.strDrink

        Picasso.get()
            .load(cocktail.strDrinkThumb)
            .placeholder(R.mipmap.ic_launcher) // Imagen de carga
            .error(R.mipmap.ic_launcher_round) // Imagen de error
            .into(holder.binding.ivCocktailImage)

        // El clic en el elemento completo se sigue manejando sobre la raíz (itemView o binding.root)
        holder.itemView.setOnClickListener {
            onItemClick(cocktail)
        }
    }

    override fun getItemCount(): Int = cocktailList.size

    // Método para actualizar la lista desde el Activity/Fragment
    fun updateData(newList: List<Cocktail>) {
        cocktailList = newList
        notifyDataSetChanged()
    }
}