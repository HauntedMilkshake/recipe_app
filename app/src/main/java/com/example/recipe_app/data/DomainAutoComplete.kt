package com.example.recipe_app.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)

data class DomainAutoComplete(
    @JsonProperty val id: Int,
    @JsonProperty val title: String? = null,
    @JsonProperty val imageType: String?= null,
)
