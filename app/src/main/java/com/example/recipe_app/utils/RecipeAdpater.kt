package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.BareRecipe
import com.example.recipe_app.data.Recipe
class RecipeAdapter : Adapter<Recipe, BareRecipe>  {
    override fun adapt(t: Recipe): BareRecipe? {
        return if(t.image == null || t.title == null){
            null
        }else{
            BareRecipe(t.id, t.title, t.image)
        }
    }
}