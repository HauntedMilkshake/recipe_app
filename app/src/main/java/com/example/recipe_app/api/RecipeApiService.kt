package com.example.recipe_app.api

import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.data.FoodDetect
import com.example.recipe_app.data.Ingredient
import com.example.recipe_app.data.Instructions
import com.example.recipe_app.data.Recipe

interface RecipeApiService {
    suspend fun getRecipesByComplexSearch(query: String): List<Recipe>
    suspend fun getRandomRecipe(): EnhancedRecipe?
    suspend fun getAutoComplete(query: String): List<AutoCompleteResult>
    suspend fun getRecipeById(id: Int): EnhancedRecipe?
    suspend fun getRecipeInstructionsById(id: Int): List<Instructions>
    suspend fun getRecipeByIngredientSearch(ingredient: String): List<Recipe>
    suspend fun getRecipeByNutrientSearch(minCarbs: Int, maxCarbs: Int, minProtein: Int, maxProtein: Int, minFat: Int, maxFat: Int): List<Recipe>
    suspend fun getRandomFoodTrivia(): String
    suspend fun getFoodFromFoodDetect(text: String): List<FoodDetect>
    suspend fun getIngredientsFromRecipeId(recipeId: Int): List<Ingredient>
}