//package com.example.recipe_app.api.database
//
//import androidx.room.ColumnInfo
//import androidx.room.Embedded
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import androidx.room.Relation
//@Entity(tableName = "favourite_recipes")
//data class FavouriteRecipes(
//    @PrimaryKey val id: Int,
//    @ColumnInfo(name = "recipe_title") val title: String,
//    @ColumnInfo(name = "is_vegetarian") val isVegetarian: Boolean,
//    @ColumnInfo(name = "image_url") val imageUrl: String,
//    @ColumnInfo(name = "is_vegan") val isVegan: Boolean,
//    @ColumnInfo(name = "is_gluten_free") val isGlutenFree: Boolean,
//    @ColumnInfo(name = "is_dairy_free") val isDairyFree: Boolean,
//    @ColumnInfo(name = "minutes") val minutes: Int,
//    @ColumnInfo(name = "servings") val servings: Int,
//    @ColumnInfo(name = "ingredients_id") val ingredientsId: Long
//)
//
//@Entity(tableName = "ingredients")
//data class Ingredient(
//    @PrimaryKey(autoGenerate = true) val id: Long = 0,
//    val name: String,
//    val amount: Float,
//    val unit: String
//)
//
//data class FavouriteRecipeWithIngredients(
//    @Embedded val recipe: FavouriteRecipes,
//    @Relation(
//        parentColumn = "ingredients_id",
//        entityColumn = "id"
//    )
//    val ingredients: List<Ingredient>
//)
//
