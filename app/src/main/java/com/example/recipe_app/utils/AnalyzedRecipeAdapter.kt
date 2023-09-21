package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.AnalyzedRecipe
import com.example.recipe_app.data.AnalyzedRecipeResponse

class AnalyzedRecipeAdapter : Adapter<AnalyzedRecipeResponse, AnalyzedRecipe> {
    override fun adapt(t: AnalyzedRecipeResponse): AnalyzedRecipe? {
        return if (t.title == null || t.vegetarian == null || t.image == null || t.vegetarian == null || t.vegan == null || t.glutenFree == null || t.dairyFree == null || t.readyInMinutes == null || t.servings == null || t.instructions == null || t.ingredients == null) {
            null
        } else {
            return AnalyzedRecipe(
                id = t.id,
                title = t.title,
                isVegetarian = t.vegetarian,
                imageUrl = t.image,
                isVegan = t.vegan,
                isGlutenFree = t.glutenFree,
                isDairyFree = t.dairyFree,
                minutes = t.readyInMinutes,
                servings = t.servings,
                instructions = adaptInstructions(t.instructions),
                ingredients = adaptIngredients(t.ingredients)
            )
        }
    }

    fun adaptInstructions(instructions: List<AnalyzedRecipeResponse.Instruction>?): List<AnalyzedRecipe.Instruction> {
        return instructions?.mapIndexed { index, it ->
            AnalyzedRecipe.Instruction(
                stepNumber = index + 1, // Assuming step numbers start from 1
                instruction = it.instruction ?: ""
            )
        } ?: emptyList()
    }
//    fun adaptInstructions(instructions: List<AnalyzedRecipeResponse.Instruction>?): List<AnalyzedRecipe.Instruction>{
//        return instructions?.map {
//            AnalyzedRecipe.Instruction(
//                stepNumber = it.number ?: 0,
//                instruction = it.instruction ?: ""
//            )
//        } ?: emptyList()
//    }


    fun adaptIngredients(ingredients: List<AnalyzedRecipeResponse.Ingredient>?): List<AnalyzedRecipe.Ingredient> {
        return ingredients?.map {
            AnalyzedRecipe.Ingredient(
                name = it.name ?: "",
                amount = (it.amount ?: 0.0) as Float, // Use 0.0 for a default value
                unit = it.unit ?: ""
            )
        } ?: emptyList()
    }
}
