package com.example.thecocktaildbapi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thecocktaildbapi.R
import com.example.thecocktaildbapi.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val cocktailId = intent.getStringExtra("COCKTAIL_ID")
            val fragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putString("COCKTAIL_ID", cocktailId)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
