package com.nayab.recipebook.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.nayab.recipebook.data.Recipe
import com.nayab.recipebook.databinding.ActivityAddRecipeBinding
import com.nayab.recipebook.viewmodel.RecipeViewModel

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding
    private val viewModel: RecipeViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data?.data

                selectedImageUri?.let { uri ->
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    binding.imagePreview.setImageURI(uri)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)

        binding.btnPickImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            imagePickerLauncher.launch(intent)
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val ingredients = binding.etIngredients.text.toString().trim()
            val steps = binding.etSteps.text.toString().trim()

            if (title.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val recipe = Recipe(
                title = title,
                ingredients = ingredients,
                steps = steps,
                imageUri = selectedImageUri?.toString(),
                imageResName = null // Drawable not used here
            )

            viewModel.insert(recipe)
            Toast.makeText(this, "Recipe saved!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
