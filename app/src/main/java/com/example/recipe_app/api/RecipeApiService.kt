package com.example.recipe_app.api

import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.BareRecipe

interface RecipeApiService {

    suspend fun getRecipesByComplexSearch(query: String): List<BareRecipe>
    suspend fun getRandomRecipe(): BareRecipe?
    suspend fun getAutoComplete(query: String): List<AutoCompleteResult>
}