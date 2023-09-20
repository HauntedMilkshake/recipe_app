package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.Nutrient
import com.example.recipe_app.data.NutrientInformationResponse

class NutrientAdapter: Adapter<NutrientInformationResponse, Nutrient> {
    override fun adapt(t: NutrientInformationResponse): Nutrient? {
        return if (t.name == null || t.amount == null || t.unit == null){
            null
        }else{
            Nutrient(
                name = t.name,
                amount = t.amount,
                unit = t.unit
            )
        }
    }
}