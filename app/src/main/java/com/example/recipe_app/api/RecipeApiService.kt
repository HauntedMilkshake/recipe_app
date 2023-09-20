package com.example.recipe_app.api

import com.example.recipe_app.data.AnalyzedRecipe
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.RecipeResponse

interface RecipeApiService {

    suspend fun getRecipesByComplexSearch(query: String): List<RecipeResponse>
    suspend fun getRandomRecipe(): AnalyzedRecipe?
    suspend fun getAutoComplete(query: String): List<AutoCompleteResult>
    //suspend fun getRecipeById(id: Int): AnalyzedRecipe?
}