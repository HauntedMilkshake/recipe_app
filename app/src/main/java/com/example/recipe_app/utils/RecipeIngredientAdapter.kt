package com.example.recipe_app.utils

import IngredientResponse
import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.Ingredient


class RecipeIngredientAdapter: Adapter<IngredientResponse.Ingredient, Ingredient> {
    override fun adapt(t: IngredientResponse.Ingredient): Ingredient? {
        return if(t.name == null || t.amount == null || t.amount.metric == null || t.amount.metric.value == null || t.amount.metric.unit == null){
             null
        }else{
            Ingredient(
                name = t.name,
                unit = t.amount.metric.unit,
                value = t.amount.metric.value
            )
        }
    }
}