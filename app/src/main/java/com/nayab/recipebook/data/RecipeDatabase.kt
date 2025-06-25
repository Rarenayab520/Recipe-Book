package com.nayab.recipebook.data

import android.content.Context
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 2, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                getDatabase(context).recipeDao().apply {
                                    insert(Recipe(
                                        title = "Mango Shake",
                                        ingredients = "Milk, Mango, Sugar",
                                        steps = "Peel and cut mangoes. Add to blender with milk and sugar. Blend until smooth and serve chilled.",
                                        imageResName = "mango_shake",
                                        imageUri = null
                                    ))
                                    insert(Recipe(
                                        title = "Mint Margarita",
                                        ingredients = "Mint, Lemon, Ice, Soda",
                                        steps = "Add mint, lemon juice, soda, and ice in a blender. Blend until frothy. Serve with lemon slice.",
                                        imageResName = "mint_margarita",
                                        imageUri = null
                                    ))
                                    insert(Recipe(
                                        title = "Chicken Biryani",
                                        ingredients = "Rice, Chicken, Spices",
                                        steps = "Marinate and cook chicken with spices. Boil rice with whole spices. Layer chicken and rice, steam for 10 minutes.",
                                        imageResName = "chicken_biryani",
                                        imageUri = null
                                    ))
                                    insert(Recipe(
                                        title = "Tea",
                                        ingredients = "Water, Tea leaves, Milk, Sugar",
                                        steps = "Boil water and add tea leaves. Add sugar and milk, simmer for a few minutes. Strain and serve hot.",
                                        imageResName = "tea",
                                        imageUri = null
                                    ))
                                    insert(Recipe(
                                        title = "Cold Coffee",
                                        ingredients = "Milk, Coffee, Sugar, Ice",
                                        steps = "Add milk, coffee, sugar, and ice in a blender. Blend well until frothy. Serve chilled.",
                                        imageResName = "cold_coffee",
                                        imageUri = null
                                    ))
                                    insert(Recipe(
                                        title = "Coffee",
                                        ingredients = "Water, Instant Coffee, Sugar",
                                        steps = "Boil water, mix in coffee and sugar. Stir until fully dissolved. Add hot milk and serve.",
                                        imageResName = "coffee",
                                        imageUri = null
                                    ))
                                    insert(Recipe(
                                        title = "Chicken Karahi",
                                        ingredients = "Chicken, Tomatoes, Spices",
                                        steps = "Cook chicken with tomatoes and spices. Stir until oil separates. Garnish with coriander and green chilies.",
                                        imageResName = "chicken_karahi",
                                        imageUri = null
                                    ))
                                    insert(Recipe(
                                        title = "Lasagna",
                                        ingredients = "Pasta sheets, Chicken/Beef, Cheese, Sauce",
                                        steps = "Cook meat sauce and boil pasta sheets. Layer sauce, cheese, and sheets. Bake until golden and bubbly.",
                                        imageResName = "lasagna",
                                        imageUri = null
                                    ))
                                    insert(Recipe(
                                        title = "Noodles",
                                        ingredients = "Noodles, Vegetables, Sauces",
                                        steps = "Boil noodles. Stir-fry vegetables, add sauces. Mix in noodles and toss well. Serve hot.",
                                        imageResName = "noodles",
                                        imageUri = null
                                    ))
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
