package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.Recipe
import com.example.recipe_app.data.RecipeResponse

class RecipeAdapter : Adapter<RecipeResponse, Recipe>  {
    override fun adapt(t: RecipeResponse): Recipe? {
        return if(t.image == null || t.title == null){
            null
        }else{
            Recipe(
                id = t.id,
                title = t.title,
                image = t.image
            )
        }
    }
}