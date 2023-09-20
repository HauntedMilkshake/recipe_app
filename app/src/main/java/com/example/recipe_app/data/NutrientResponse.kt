package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)

data class NutrientResponse(
    @JsonProperty("nutrients") val nutrients: List<NutrientInformationResponse?>
)
data class NutrientInformationResponse(
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("amount") val amount: Float? = null,
    @JsonProperty("unit") val unit: String? = null,
)


