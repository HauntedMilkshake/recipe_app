package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AutoCompleteResultApiResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String? = null,
)
