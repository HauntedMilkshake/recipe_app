package com.example.recipe_app.utils

import com.example.recipe_app.common.Adapter
import com.example.recipe_app.data.FoodDetect
import com.example.recipe_app.data.FoodDetectResponse

class FoodDetectAdapter: Adapter<FoodDetectResponse.AnnotationWrapper, FoodDetect> {
    override fun adapt(t: FoodDetectResponse.AnnotationWrapper): FoodDetect? {
        return if(t.annotation == null || t.image == null){
            null
        }else{
            FoodDetect(
                annotation = t.annotation,
                imageUrl = t.image
            )
        }
    }
}