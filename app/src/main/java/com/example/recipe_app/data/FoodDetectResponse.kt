package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FoodDetectResponse(
    @JsonProperty("annotations") val annotations: List<AnnotationWrapper>? = emptyList()

){
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class AnnotationWrapper(
        @JsonProperty("annotation") val annotation: String? = null,
        @JsonProperty("image") val image: String? = null,
        )
}
