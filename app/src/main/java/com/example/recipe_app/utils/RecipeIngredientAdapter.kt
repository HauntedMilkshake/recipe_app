package com.example.recipe_app.utils

import IngredientResponse
import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.Ingredients


class RecipeIngredientAdapter: Adapter<IngredientResponse.Ingredient, Ingredients.Ingredient> {
    override fun adapt(t: IngredientResponse.Ingredient): Ingredients.Ingredient? {
        return if(t.amount == null || t.name == null){
             null
        }else{
             Ingredients.Ingredient(
                name = t.name,
                amount = adaptAmount(t.amount)!!
            )
        }
    }
    private fun adaptAmount(t: IngredientResponse.Ingredient.Amount): Ingredients.Ingredient.Amount? {
        return if(t.metric == null){
            null
        }else{
            Ingredients.Ingredient.Amount(
                metric = adaptMetric(t.metric)!!
            )
        }
    }
    private fun adaptMetric(t: IngredientResponse.Ingredient.Amount.Metric): Ingredients.Ingredient.Amount.Metric? {
        return if(t.unit == null || t.value == null){
            null
        }else{
            Ingredients.Ingredient.Amount.Metric(
                unit = t.unit,
                value = t.value
            )
        }
    }



}