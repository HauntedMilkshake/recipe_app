package com.example.recipe_app.utils

import com.example.recipe_app.api.database.FavouriteRecipes
import com.example.recipe_app.api.database.Ingredient
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.data.Ingredients

fun Ingredients.Ingredient.toRoomIngredient(recipeId: Int): Ingredient {
    return Ingredient(
        recipeId = recipeId,
        name = this.name,
        amount = this.amount.metric.value,
        unit = this.amount.metric.unit
    )
}

fun EnhancedRecipe.toRoomRecipe(): FavouriteRecipes {
    return FavouriteRecipes(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        isGlutenFree = this.isGlutenFree,
        isVegan = this.isVegan,
        isDairyFree = this.isDairyFree,
        isVegetarian = this.isVegetarian,
        minutes = this.minutes,
        servings = this.servings,
    )
}

fun FavouriteRecipes.toEnhancedRecipe(): EnhancedRecipe {
    return EnhancedRecipe(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        isGlutenFree = this.isGlutenFree,
        isVegan = this.isVegan,
        isDairyFree = this.isDairyFree,
        isVegetarian = this.isVegetarian,
        minutes = this.minutes,
        servings = this.servings
    )
}