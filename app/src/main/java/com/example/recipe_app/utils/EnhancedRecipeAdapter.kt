package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.data.EnhancedRecipeResponse

class EnhancedRecipeAdapter : Adapter<EnhancedRecipeResponse.EnhancedRecipe, EnhancedRecipe> {
    override fun adapt(t: EnhancedRecipeResponse.EnhancedRecipe): EnhancedRecipe? {
        return if ((t.title == null) || (t.vegetarian == null) || (t.image == null) || (t.vegan == null) || (t.glutenFree == null) || (t.dairyFree == null) || (t.readyInMinutes == null) || (t.servings == null)) {
            null
        } else {
            return EnhancedRecipe(
                id = t.id,
                title = t.title,
                isVegetarian = t.vegetarian,
                imageUrl = t.image,
                isVegan = t.vegan,
                isGlutenFree = t.glutenFree,
                isDairyFree = t.dairyFree,
                minutes = t.readyInMinutes,
                servings = t.servings,
                ingredients = adaptIngredients(t.extendedIngredients)
            )
        }
    }
    fun adaptIngredients(ingredients: List<EnhancedRecipeResponse.EnhancedRecipe.Ingredient>?): List<EnhancedRecipe.Ingredient> {
        return ingredients?.map {
            EnhancedRecipe.Ingredient(
                name = it.name ?: "",
                amount = it.amount ?: 0.0f,
                unit = it.unit ?: ""
            )
        } ?: emptyList()
    }
}
