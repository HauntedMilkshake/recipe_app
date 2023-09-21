package com.example.recipe_app.api

import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.data.Instructions
import com.example.recipe_app.data.Recipe

interface RecipeApiService {

    suspend fun getRecipesByComplexSearch(query: String): List<Recipe>
    suspend fun getRandomRecipe(): EnhancedRecipe?
    suspend fun getAutoComplete(query: String): List<AutoCompleteResult>
    suspend fun getRecipeById(id: Int): EnhancedRecipe?
    suspend fun getRecipeInstructionsById(id: Int): List<Instructions>

    suspend fun getRecipeByIngredientSearch(query: String): List<Recipe>
    suspend fun getRecipeByNutrientSearch(query: String): List<Recipe>
}