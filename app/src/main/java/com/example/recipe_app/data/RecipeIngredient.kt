package com.example.recipe_app.data

data class Ingredients(
    val ingredients: List<Ingredient>
) {

    data class Ingredient(
        val amount: Amount,
        val name: String
    ) {
        data class Amount(
            val metric: Metric
        ) {
            data class Metric(
                val unit: String,
                val value: Float
            )
        }
    }
}
