import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class IngredientResponse(
    @JsonProperty("ingredients") val ingredients: List<Ingredient>?
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Ingredient(
        @JsonProperty("amount") val amount: Amount? = null,
        @JsonProperty("name") val name: String? = null
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Amount(
            @JsonProperty("metric") val metric: Metric? = null
        ) {
            @JsonIgnoreProperties(ignoreUnknown = true)
            data class Metric(
                @JsonProperty("unit") val unit: String? = null,
                @JsonProperty("value") val value: Float? = null
            )
        }
    }
}
