package com.example.recipe_app.information

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.R
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.getApiService
import com.example.recipe_app.getRecipeDatabase
import com.example.recipe_app.utils.toEnhancedRecipe
import com.example.recipe_app.utils.toRoomIngredient
import com.example.recipe_app.utils.toRoomInstruction
import com.example.recipe_app.utils.toRoomRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentRecipeInformationViewModel(application: Application): AndroidViewModel(application) {
    private val api = application.getApiService()
    private val database = application.getRecipeDatabase()
    private val _enhancedRecipe = MutableLiveData<EnhancedRecipe>()
    val enhancedRecipe: LiveData<EnhancedRecipe> get() = _enhancedRecipe
    private val _isFavourited = MutableLiveData<Boolean>(false)
    val isFavourited: LiveData<Boolean> get() = _isFavourited
    private val _vladiMirishe = MutableLiveData<String>()
    val vladiMirishe: LiveData<String> get() = _vladiMirishe

    //in spirits of optimization we know that we dont need api requests when coming from favourites,
    // we know that we might need an api request when coming from home and it is just easier
    // to assume that we dont have the rest of the recipies
    fun checkIfRecipeExistsAndGet(previousFragmentId: Int, recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (previousFragmentId) {
                R.id.favourites -> {
                    _isFavourited.postValue(true)
                    _enhancedRecipe.postValue(
                        database.getFavouriteRecipeById(recipeId)!!.toEnhancedRecipe()
                    )
                }
                R.id.home -> {
                    val favouriteRecipeTry = database.getFavouriteRecipeById(recipeId)
                    if (favouriteRecipeTry != null) {
                        _enhancedRecipe.postValue(favouriteRecipeTry!!.toEnhancedRecipe())
                        _isFavourited.postValue(true)
                    } else {
                        _enhancedRecipe.postValue(api.getRecipeById(recipeId))
                    }
                }
                R.id.search -> {
                    val favouriteRecipeTry = database.getFavouriteRecipeById(recipeId)
                    if (favouriteRecipeTry != null) {
                        _enhancedRecipe.postValue(favouriteRecipeTry!!.toEnhancedRecipe())
                        _isFavourited.postValue(true)
                    } else {
                        _enhancedRecipe.postValue(api.getRecipeById(recipeId))
                    }
                }
                else -> {
                    _enhancedRecipe.postValue(api.getRecipeById(recipeId))
                }
            }
        }
    }
    fun favouriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavourited.postValue(false)
            database.insertRecipe(enhancedRecipe.value!!.toRoomRecipe())
            database.insertIngredients(api.getIngredientsFromRecipeId(enhancedRecipe.value!!.id).mapNotNull{ it.toRoomIngredient(enhancedRecipe.value!!.id) })
            database.insertInstructions(api.getRecipeInstructionsById(enhancedRecipe.value!!.id).mapNotNull{ it.toRoomInstruction(enhancedRecipe.value!!.id) })
        }
    }

    fun removeFromFavourites(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            // Move database operations to the background thread
            _isFavourited.postValue(false)
            database.deleteRecipeById(recipeId)
            database.deleteIngredientByRecipeId(recipeId)
            database.deleteInstructionsForRecipe(recipeId
            )
        }
    }
}


