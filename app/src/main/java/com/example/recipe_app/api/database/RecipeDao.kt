//package com.example.recipe_app.api.database
//
//
//
//import android.content.Context
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Room
//import androidx.room.Update
//
//@Dao
//interface RecipeDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertRecipe(recipe: FavouriteRecipes)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertRecipes(recipes: List<FavouriteRecipes>)
//    @Update
//    suspend fun updateRecipe(recipe: FavouriteRecipes)
//
//    @Delete
//    suspend fun deleteRecipe(recipe: FavouriteRecipes)
//
//    @Query("SELECT * FROM favourite_recipes")
//    suspend fun getAllFavoriteRecipes(): List<FavouriteRecipeWithIngredients>
//
//    @Query("SELECT * FROM favourite_recipes WHERE id = :recipeId")
//    suspend fun getRecipeById(recipeId: Int): FavouriteRecipeWithIngredients?
//
//    @Query("DELETE FROM favourite_recipes WHERE id = :recipeId")
//    suspend fun deleteRecipeById(recipeId: Int)
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertIngredient(ingredient: Ingredient)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertIngredients(ingredients: List<Ingredient>)
//
//    @Update
//    suspend fun updateIngredient(ingredient: Ingredient)
//
//    @Delete
//    suspend fun deleteIngredient(ingredient: Ingredient)
//
//    @Query("SELECT * FROM ingredients")
//    suspend fun getAllIngredients(): List<Ingredient>
//
//    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
//    suspend fun getIngredientById(ingredientId: Long): Ingredient?
//
//    @Query("DELETE FROM ingredients WHERE id = :ingredientId")
//    suspend fun deleteIngredientById(ingredientId: Long)
//}
//
//object DaoProvider {
//    private var instance: RecipeDao? = null
//
//    fun getRecipeDao(context: Context): RecipeDao {
//        return instance ?: synchronized(this) {
//            instance ?: createDatabase(context).recipeDao().also { instance = it }
//        }
//    }
//
//    private fun createDatabase(context: Context): RecipeDatabase {
//        return Room.databaseBuilder(context.applicationContext, RecipeDatabase::class.java, "max_e_smeshen").build()
//    }
//}
//
