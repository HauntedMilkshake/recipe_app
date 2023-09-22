package com.example.recipe_app.information

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class FragmentRecipeInformationViewModel(application: Application): AndroidViewModel(application) {
    private val api = application.getApiService()
//    private val database = application.getRecipeDatabase()
    private val _enhancedRecipe = MutableLiveData<EnhancedRecipe>()
    val enhancedRecipe: LiveData<EnhancedRecipe> get() = _enhancedRecipe
    fun getRecipe(id: Int){
        viewModelScope.launch {
            _enhancedRecipe.postValue(api.getRecipeById(id))
            Log.d("LiveData", enhancedRecipe.value.toString())
        }
    }
//    fun insertRecipe(recipe: EnhancedRecipe){
//        viewModelScope.launch {
//            database.insertRecipe(recipe)
//        }
//    }
//    private fun convertToRoomRecipe(recipe: EnhancedRecipe?): FavouriteRecipes = FavouriteRecipes(id = recipe!!.id, title = recipe.title, isVegetarian = recipe.isVegetarian, imageUrl = recipe.imageUrl, isVegan = recipe.isVegan, isGlutenFree = recipe.isGlutenFree, isDairyFree = recipe.isDairyFree, minutes = recipe.minutes, servings = recipe.servings, ingredients = convertEnhancedIngredientsToRoomIngredients(recipe.ingredients))
//    private fun convertEnhancedIngredientsToRoomIngredients(ingredients: List<EnhancedRecipe.Ingredient>): List<FavouriteRecipes.Ingredient>{
//        return ingredients.mapNotNull{
//            FavouriteRecipes.Ingredient(
//                name = it.name,
//                amount = it.amount,
//                unit = it.unit
//            )
//        }
//    }
}