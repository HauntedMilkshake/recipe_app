package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.Recipe
import com.example.recipe_app.data.RecipeResponse
class RecipeAdapter : Adapter<Recipe, RecipeResponse>  {
    override fun adapt(t: Recipe): RecipeResponse? {
        return if(t.image == null || t.title == null){
            null
        }else{
            RecipeResponse(t.id, t.title, t.image)
        }
    }
}