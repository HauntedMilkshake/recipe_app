package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

@JsonIgnoreProperties(ignoreUnknown = true)
data class Response(
    @JsonProperty("recipes") val responses: List<AnalyzedRecipeResponse>?
) {
    data class AnalyzedRecipeResponse(
        @JsonProperty("vegetarian") val vegetarian: Boolean? = null,
        @JsonProperty("vegan") val vegan: Boolean? = null,
        @JsonProperty("glutenFree") val glutenFree: Boolean? = null,
        @JsonProperty("dairyFree") val dairyFree: Boolean? = null,
        @JsonProperty("id") val id: Int,
        @JsonProperty("title") val title: String? = null,
        @JsonProperty("image") val image: String? = null,
        @JsonProperty("readyInMinutes") val readyInMinutes: Int? = null,
        @JsonProperty("servings") val servings: Int? = null,
        @JsonProperty("extendedIngredients") val ingredients: List<Ingredient>? = null,
        @JsonProperty("analyzedInstructions") val instructions: List<Instruction>? = null
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Ingredient(
        @JsonProperty("name") val name: String? = null,
        @JsonProperty("amount") val amount: Float? = null,
        @JsonProperty("unit") val unit: String? = null
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Instruction(
        @JsonProperty("number") val number: Int? = null,
        @JsonProperty("step") val instruction: String? = null
    )
    companion object {
        val objectMapper: ObjectMapper = ObjectMapper()
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    }
}

