package com.example.recipe_app.utils

import com.example.recipe_app.api.database.FavouriteRecipes
import com.example.recipe_app.api.database.Ingredient
import com.example.recipe_app.api.database.Instruction
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.data.Instructions

fun com.example.recipe_app.data.Ingredient.toRoomIngredient(recipeId: Int): com.example.recipe_app.api.database.Ingredient {
    return com.example.recipe_app.api.database.Ingredient(
        recipeId = recipeId,
        name = this.name,
        amount = this.value,
        unit = this.unit
    )
}
fun Ingredient.toRecipeIngredient(recipeId: Int): com.example.recipe_app.data.Ingredient{
    return com.example.recipe_app.data.Ingredient(
        name = this.name,
        unit = this.unit,
        value = this.amount
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

fun Instructions.toRoomInstruction(recipeId: Int): Instruction{
    return Instruction(
        recipeId = recipeId,
        stepNumber = this.number,
        instruction = this.step
    )
}
fun Instruction.toRecipeInstructions(): Instructions {
    return Instructions(
        number = this.stepNumber,
        step = this.instruction
    )
}