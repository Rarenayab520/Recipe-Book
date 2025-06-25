package com.nayab.recipebook.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nayab.recipebook.R
import com.nayab.recipebook.data.Recipe
import com.nayab.recipebook.databinding.ItemRecipeBinding

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.tvTitle.text = recipe.title
            binding.tvIngredients.text = recipe.ingredients

            val context = binding.root.context


            if (recipe.imageUri.isNullOrEmpty()) {
                // Check if this is a predefined recipe
                if (!recipe.imageResName.isNullOrEmpty()) {
                    val resId = context.resources.getIdentifier(
                        recipe.imageResName,
                        "drawable",
                        context.packageName
                    )
                    if (resId != 0) {
                        Glide.with(context)
                            .load(resId)
                            .into(binding.imageRecipe)
                    } else {
                        binding.imageRecipe.setImageResource(R.drawable.placeholder)
                    }
                } else {
                    // Na drawable mila, na URI -> placeholder
                    binding.imageRecipe.setImageResource(R.drawable.placeholder)
                }
            } else {
                // Manually added recipe -> gallery image se load
                Glide.with(context)
                    .load(Uri.parse(recipe.imageUri))
                    .into(binding.imageRecipe)
            }

            binding.root.setOnClickListener {
                onItemClick(recipe)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    fun updateList(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}
