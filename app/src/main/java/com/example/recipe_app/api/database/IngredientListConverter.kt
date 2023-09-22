//package com.example.recipe_app.api.database
//
//import androidx.room.TypeConverter
//import com.fasterxml.jackson.core.type.TypeReference
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
//
//class IngredientListConverter {
//    private val objectMapper: ObjectMapper = jacksonObjectMapper()
//
//    @TypeConverter
//    fun fromList(value: String): List<Ingredient> {
//        return objectMapper.readValue(value, object : TypeReference<List<FavouriteRecipes.Ingredient>>() {})
//    }
//
//    @TypeConverter
//    fun toList(ingredients: List<FavouriteRecipes.Ingredient>): String {
//        return objectMapper.writeValueAsString(ingredients)
//    }
//}
