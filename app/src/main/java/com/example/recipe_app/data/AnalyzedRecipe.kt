package com.example.recipe_app.data

data class EnhancedRecipe(
    val id: Int,
    val title: String,
    val isVegetarian: Boolean,
    val imageUrl: String,
    val isVegan: Boolean,
    val isGlutenFree: Boolean,
    val isDairyFree: Boolean,
    val minutes: Int,
    val servings: Int,
)

