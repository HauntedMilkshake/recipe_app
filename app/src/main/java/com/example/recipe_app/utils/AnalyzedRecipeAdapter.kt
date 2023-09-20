package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.AnalyzedRecipeDomain
import com.example.recipe_app.data.AnalyzedRecipeResponse

class AnalyzedRecipeAdapter: Adapter<AnalyzedRecipeResponse, AnalyzedRecipeDomain> {
    override fun adapt(t: AnalyzedRecipeResponse): AnalyzedRecipeDomain? {
        return if(t.title == null || t.isDairyFree == null || t.isGlutenFree == null || t.isVegan == null || t.isVegeterian == null || t.minutes == null || t.servings == null){
            null
        }else{
            AnalyzedRecipeDomain(id = t.id, title = t.title, isDairyFree = t.isDairyFree, isGlutenFree = t.isGlutenFree, isVegan = t.isVegan, isVegeterian = t.isVegeterian, minutes = t.minutes, servings = t.servings)
        }
    }
}