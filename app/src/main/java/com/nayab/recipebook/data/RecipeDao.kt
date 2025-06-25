package com.nayab.recipebook.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipe)

    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun getAll(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE title LIKE '%' || :query || '%' OR ingredients LIKE '%' || :query || '%'")
    fun search(query: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table")
    suspend fun getAllNow(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(recipes: List<Recipe>)


}


