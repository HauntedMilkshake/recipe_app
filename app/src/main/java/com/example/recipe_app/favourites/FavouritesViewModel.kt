package com.example.recipe_app.favourites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.api.database.FavouriteRecipes
import com.example.recipe_app.getRecipeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {
    private val database = application.getRecipeDatabase()
    private val _favouriteRecipes = MutableLiveData<List<FavouriteRecipes>>(emptyList())
    val favouriteRecipes: LiveData<List<FavouriteRecipes>> = _favouriteRecipes
    init {
        getRecipesFromRoom()
    }
    private fun getRecipesFromRoom() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.getAllFavouriteRecipesWithoutIngredients().collect {
                    _favouriteRecipes.postValue(it)
                }
            }
        }
    }
    fun removeRecipeFromRoom(recipeId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.deleteRecipeById(recipeId)
                database.deleteIngredientByRecipeId(recipeId)
            }
        }
    }
}



