package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiRecipeResponse(
    @JsonProperty("results") val results: List<RecipeResponse?> = emptyList(),
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RecipeResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("image") val image: String? = null,
)