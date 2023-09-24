package com.example.recipe_app.api.database



import android.content.Context
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Room
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {
    @Upsert
    fun insertInstruction(instruction: Instruction)
    @Upsert
    fun insertInstructions(instructions: List<Instruction>)
    @Query("SELECT * FROM instructions WHERE recipeId = :recipeId")
    fun getInstructionsForRecipeById(recipeId: Int): Flow<List<Instruction>>
    @Query("DELETE FROM instructions WHERE recipeId = :recipeId")
    fun deleteInstructionsForRecipe(recipeId: Int)
    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    fun getIngredientsForRecipe(recipeId: Int): Flow<List<Ingredient>>

    @Upsert
    fun insertRecipe(recipe: FavouriteRecipes)

    @Upsert
    fun insertRecipes(recipes: List<FavouriteRecipes>)

    @Delete
    fun deleteRecipe(recipe: FavouriteRecipes)
    @Upsert
    fun insertIngredient(ingredient: Ingredient)

    @Upsert
    fun insertIngredients(ingredients: List<Ingredient>)

    @Delete
     fun deleteIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM favourite_recipes")
    fun getAllFavouriteRecipesWithoutIngredients(): Flow<List<FavouriteRecipes>>

    @Query("DELETE FROM favourite_recipes WHERE id = :id")
     fun deleteRecipeById(id: Int)
     @Query("SELECT * FROM favourite_recipes WHERE id=:id")
     fun getFavouriteRecipeById(id: Int): FavouriteRecipes?

    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
    fun getIngredientById(ingredientId: Long): Ingredient?


    @Query("DELETE FROM ingredients WHERE id = :ingredientId")
    fun deleteIngredientById(ingredientId: Long)

    @Query("DELETE FROM ingredients WHERE recipeId = :recipeId")
    fun deleteIngredientByRecipeId(recipeId: Int)
}

object DaoProvider {
    private var instance: RecipeDao? = null
    fun getRecipeDao(context: Context): RecipeDao {
        return instance ?: synchronized(this) {
            instance ?: createDatabase(context).recipeDao().also { instance = it }
        }
    }
    private fun createDatabase(context: Context): RecipeDatabase {
        return Room.databaseBuilder(context.applicationContext, RecipeDatabase::class.java, "max_e_smeshen").build()
    }
}

