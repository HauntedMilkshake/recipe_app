package com.example.recipe_app.api

import com.example.recipe_app.data.DomainRecipe

interface RecipeApiService {
    suspend fun getRecipes()
    suspend fun getSingleRecipe(query: String): DomainRecipe
}