package com.example.recipe_app.data
data class AnalyzedRecipeDomain(
    val id: Int,
    val title: String? = null,
    val isVegeterian: Boolean? = null,
    val isVegan: Boolean? = null,
    val isGlutenFree: Boolean? = null,
    val isDairyFree: Boolean? = null,
    val minutes: Float? = null,
    val servings: Float? = null,
    //val analyzedInstrucionts: AnalyzedInstructions? = null
)
