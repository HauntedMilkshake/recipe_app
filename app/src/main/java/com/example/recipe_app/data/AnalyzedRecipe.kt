package com.example.recipe_app.data

data class AnalyzedRecipe(
//    val id: Int,
    val title: String,
    val isVegeterian: Boolean,
    val imageUrl: String,
    val isVegan: Boolean,
    val isGlutenFree: Boolean,
    val isDairyFree: Boolean,
    val minutes: Int,
    val servings: Int,
    val instructions: List<Instruction>,
    val ingredients: List<Ingredient>
) {
    data class Ingredient(
        val name: String,
        val amount: Float,
        val unit: String
    )
    data class Instruction(
        val stepNumber: Int,
        val instruction: String
    )
}
