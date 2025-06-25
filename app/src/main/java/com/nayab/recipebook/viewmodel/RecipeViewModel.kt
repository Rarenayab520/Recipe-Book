package com.nayab.recipebook.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.nayab.recipebook.data.Recipe
import com.nayab.recipebook.data.RecipeDatabase
import com.nayab.recipebook.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository
    private val _searchResults = MediatorLiveData<List<Recipe>>()

    val allRecipes: LiveData<List<Recipe>>
    val searchResults: LiveData<List<Recipe>> get() = _searchResults

    init {
        val dao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(dao)
        allRecipes = repository.getAllRecipes()

        // By default show all recipes
        _searchResults.addSource(allRecipes) {
            _searchResults.value = it
        }
    }

    fun insert(recipe: Recipe) = viewModelScope.launch {
        repository.insertRecipe(recipe)
    }

    fun search(query: String) {
        val source = repository.searchRecipes(query)
        _searchResults.addSource(source) {
            _searchResults.value = it
            _searchResults.removeSource(source) // 🧹 Clean after one-time use
        }
    }

    fun resetSearch() {
        _searchResults.value = allRecipes.value
    }

    fun refreshAllRecipes() {
        _searchResults.value = allRecipes.value
    }
}
