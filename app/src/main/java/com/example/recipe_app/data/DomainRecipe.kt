package com.example.recipe_app.data
import com.fasterxml.jackson.annotation.JsonProperty

data class DomainRecipe(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String?,
    @JsonProperty("image") val image: String?,
    @JsonProperty("imageType") val imageType: String?
)
