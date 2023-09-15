package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.BareRecipe
import com.example.recipe_app.data.DomainRecipe
class RecipeAdapter : Adapter<DomainRecipe, BareRecipe>  {
    override fun adapt(t: DomainRecipe): BareRecipe? {
        return if(t.image == null || t.title == null){
            null
        }else{
            BareRecipe(t.id, t.title, t.image)
        }
    }
}