package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AnalyzedRecipeResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("vegeterian") val isVegeterian: Boolean? = null,
    @JsonProperty("vegan") val isVegan: Boolean? = null,
    @JsonProperty("glutenFree") val isGlutenFree: Boolean? = null,
    @JsonProperty("dairyFree") val isDairyFree: Boolean? = null,
    @JsonProperty("readyInMinutes") val minutes: Float? = null,
    @JsonProperty("servings") val servings: Float? = null,
    //@JsonProperty("analyzedInstructions") val analyzedInstrucionts: AnalyzedInstructions? = null
    )
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//data class AnalyzedInstructions(
//    @JsonProperty("id") val placeHolder: Int
//)
