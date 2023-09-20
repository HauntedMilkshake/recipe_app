package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.AnalyzedRecipe
import com.example.recipe_app.data.Response

//import com.example.recipe_app.data.Response
class AnalyzedRecipeAdapter: Adapter<Response.AnalyzedRecipeResponse, AnalyzedRecipe> {
    override fun adapt(t: Response.AnalyzedRecipeResponse): AnalyzedRecipe? {
        return if(t.title == null || t.vegetarian == null || t.image == null || t.vegetarian == null || t.vegan == null || t.glutenFree == null || t.dairyFree == null || t.readyInMinutes == null || t.servings == null || t.instructions == null || t.ingredients == null){
            null
        }else{
            return AnalyzedRecipe(
//                id = t.id,
                title = t.title,
                isVegeterian = t.vegetarian,
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
    fun adaptInstructions(instructions: List<Response.Instruction>?): List<AnalyzedRecipe.Instruction>{
        return instructions?.map {
                AnalyzedRecipe.Instruction(
                    stepNumber = it.number ?: 0,
                    instruction = it.instruction ?: ""
                )
            } ?: emptyList()
    }
    fun adaptIngredients(ingredients: List<Response.Ingredient>?): List<AnalyzedRecipe.Ingredient>{
        return ingredients?.map {
            AnalyzedRecipe.Ingredient(
                name = it.name ?: "",
                amount = (it.amount ?: 0) as Float, // ???
                unit = it.unit ?: ""
            )
        } ?: emptyList()
    }
}