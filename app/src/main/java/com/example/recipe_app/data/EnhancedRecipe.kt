package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class EnhancedRecipeResponse(
    @JsonProperty("recipes") val recipes: List<EnhancedRecipe>?
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class EnhancedRecipe(
        @JsonProperty("id") val id: Int,
        @JsonProperty("title") val title: String? = null,
        @JsonProperty("vegetarian") val vegetarian: Boolean? = null,
        @JsonProperty("vegan") val vegan: Boolean? = null,
        @JsonProperty("glutenFree") val glutenFree: Boolean? = null,
        @JsonProperty("dairyFree") val dairyFree: Boolean? = null,
        @JsonProperty("extendedIngredients") val extendedIngredients: List<Ingredient>? = emptyList(),
        @JsonProperty("readyInMinutes") val readyInMinutes: Int? = null,
        @JsonProperty("servings") val servings: Int? = null,
        @JsonProperty("image") val image: String? = null
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Ingredient(
            @JsonProperty("name") val name: String? = null,
            @JsonProperty("amount") val amount: Float? = null,
            @JsonProperty("unit") val unit: String? = null
        )
    }
}

