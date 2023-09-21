package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.Instructions
import com.example.recipe_app.data.InstructionsResponse

class InstructionsAdapter: Adapter<InstructionsResponse.Steps, Instructions> {
    override fun adapt(t: InstructionsResponse.Steps): Instructions? {
       return if(t.number == null || t.step == null){
           null
       }else{
          Instructions(
              number = t.number,
              step = t.step
           )
       }
    }
}