package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RecipeResponse(
    @JsonProperty("results") val results: List<Recipe?> = emptyList(),
    @JsonProperty("offset") val offset: Int? = 0,
    @JsonProperty("number") val number: Int? = 0,
    @JsonProperty("totalResults") val totalResults: Int? = 0
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Recipe(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("image") val image: String? = null,
    @JsonProperty("imageType") val imageType: String? = null
)