package com.example.recipe_app.api

import com.example.recipe_app.data.BareRecipe

interface RecipeApiService {
    suspend fun getRecipes()
    suspend fun getSingleRecipe(): BareRecipe?
}