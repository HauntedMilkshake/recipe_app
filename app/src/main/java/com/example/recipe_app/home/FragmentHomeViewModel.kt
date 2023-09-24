package com.example.recipe_app.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.api.RetrofitRecipeApiService
import com.example.recipe_app.api.database.FavouriteRecipes
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.getApiService
import com.example.recipe_app.getRecipeDatabase
import kotlinx.coroutines.launch



class FragmentHomeViewModel(application: Application): AndroidViewModel(application) {
    private var database = application.getRecipeDatabase()
    private var api: RetrofitRecipeApiService = application.getApiService()
    private val _recipe = MutableLiveData<EnhancedRecipe>()
    val recipe: LiveData<EnhancedRecipe> get() = _recipe
    private val _dbRecipes = MutableLiveData<List<FavouriteRecipes>>()
    val dbRecipes:LiveData<List<FavouriteRecipes>> get() = _dbRecipes
    init {
        fetchRecipe()
        fetchDbRecipes()
    }

    private fun fetchRecipe() {
        viewModelScope.launch {
            _recipe.postValue(api.getRandomRecipe())
        }
    }

    private fun fetchDbRecipes(){
        viewModelScope.launch {
            database.getAllFavouriteRecipesWithoutIngredients().collect{
                val capturedRecipes = it
                Log.d("REC", capturedRecipes.toString())
                _dbRecipes.postValue(capturedRecipes)
            }
        }
    }
}