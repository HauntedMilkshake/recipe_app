package com.example.recipe_app.api

interface RecipeApiService {
    suspend fun getRecipes()
    suspend fun getSingleRecipe()
}