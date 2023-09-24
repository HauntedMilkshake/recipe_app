package com.example.recipe_app.api.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "favourite_recipes")
data class FavouriteRecipes(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "recipe_title") val title: String,
    @ColumnInfo(name = "is_vegetarian") val isVegetarian: Boolean,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "is_vegan") val isVegan: Boolean,
    @ColumnInfo(name = "is_gluten_free") val isGlutenFree: Boolean,
    @ColumnInfo(name = "is_dairy_free") val isDairyFree: Boolean,
    @ColumnInfo(name = "minutes") val minutes: Int,
    @ColumnInfo(name = "servings") val servings: Int,
)

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("recipeId") val recipeId: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("amount") val amount: Float,
    @ColumnInfo("unit") val unit: String
)

