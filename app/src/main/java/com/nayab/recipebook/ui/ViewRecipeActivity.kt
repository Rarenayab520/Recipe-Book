package com.nayab.recipebook.ui

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nayab.recipebook.R
import com.nayab.recipebook.data.Recipe
import com.nayab.recipebook.databinding.ActivityViewRecipeBinding

class ViewRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = intent.getSerializableExtra("recipe") as? Recipe

        recipe?.let {
            binding.tvTitle.text = it.title
            binding.tvIngredients.text = it.ingredients
            binding.tvSteps.text = it.steps

            when {
                // 📷 Manually added → Gallery image
                !it.imageUri.isNullOrEmpty() -> {
                    Glide.with(this)
                        .load(Uri.parse(it.imageUri))
                        .into(binding.imageRecipe)
                }

                // 🎨 Predefined → Drawable image
                !it.imageResName.isNullOrEmpty() -> {
                    val imageResId = resources.getIdentifier(it.imageResName, "drawable", packageName)
                    if (imageResId != 0) {
                        Glide.with(this)
                            .load(imageResId)
                            .into(binding.imageRecipe)
                    } else {
                        binding.imageRecipe.setImageResource(R.drawable.placeholder)
                    }
                }

                // ❔ Nothing → Show placeholder
                else -> {
                    binding.imageRecipe.setImageResource(R.drawable.placeholder)
                }
            }
        }
    }
}
