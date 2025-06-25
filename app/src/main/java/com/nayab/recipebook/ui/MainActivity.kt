package com.nayab.recipebook.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.nayab.recipebook.databinding.ActivityMainBinding
import com.nayab.recipebook.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecipeAdapter
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adapter setup
        adapter = RecipeAdapter(emptyList()) { recipe ->
            val intent = Intent(this, ViewRecipeActivity::class.java)
            intent.putExtra("recipe", recipe)
            startActivity(intent)
        }

        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipeRecyclerView.adapter = adapter

        // ✅ Removed insertDefaultsIfEmpty()

        // Observe LiveData
        viewModel.allRecipes.observe(this) {
            adapter.updateList(it)
        }

        // Search functionality
        binding.searchBox.addTextChangedListener {
            val query = it.toString()
            if (query.isEmpty()) {
                viewModel.allRecipes.observe(this) { recipes ->
                    adapter.updateList(recipes)
                }
            } else {
                viewModel.search(query)
            }
        }

        viewModel.searchResults.observe(this) {
            adapter.updateList(it)
        }

        binding.fabAddRecipe.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity::class.java))
        }
    }
}
