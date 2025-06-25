package com.nayab.recipebook.repository

import com.nayab.recipebook.data.Recipe
import com.nayab.recipebook.data.RecipeDao

class RecipeRepository(private val dao: RecipeDao) {

    fun getAllRecipes() = dao.getAll()

    fun searchRecipes(query: String) = dao.search(query)

    suspend fun insertRecipe(recipe: Recipe) = dao.insert(recipe)

    suspend fun getRecipesListNow(): List<Recipe> {
        return dao.getAllNow()
    }
}
