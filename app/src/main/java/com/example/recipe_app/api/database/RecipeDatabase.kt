package com.example.recipe_app.api.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [FavouriteRecipes::class, Ingredient::class, Instruction::class], version = 1)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}