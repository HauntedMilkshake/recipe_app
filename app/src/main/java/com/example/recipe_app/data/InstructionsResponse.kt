package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class InstructionsResponse(
    @JsonProperty("steps") val instructions: List<Steps>? = emptyList()
){
    //steps directly corelates to Instructions from the other class
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Steps(
        @JsonProperty("number") val number: Int? = null,
        @JsonProperty("step") val step: String? = null
    )
}
