package com.nayab.recipebook.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val ingredients: String,
    val steps: String,
    val imageResName: String? = null,  // For drawable image
    val imageUri: String? = null       // For gallery image
): Serializable
