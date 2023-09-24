package com.example.recipe_app.instructions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.Ingredient
import com.example.recipe_app.data.Instructions
import com.example.recipe_app.getApiService
import com.example.recipe_app.getRecipeDatabase
import com.example.recipe_app.utils.toRecipeIngredient
import com.example.recipe_app.utils.toRecipeInstructions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentInstructionsViewModel(application: Application): AndroidViewModel(application) {
    private val api = application.getApiService()
    private val database = application.getRecipeDatabase()
    private val _instructions = MutableLiveData<List<Instructions>>()
    val instructions: MutableLiveData<List<Instructions>> get() = _instructions
    private val _ingredients = MutableLiveData<List<Ingredient>>()
    val ingredient: LiveData<List<Ingredient>> get() = _ingredients
    fun checkIfInformationExistsAndInitializeInformation(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val recipe = database.getFavouriteRecipeById(id)
            if(recipe != null){
                database.getInstructionsForRecipeById(id).collect{
                    val capture = it
                    _instructions.postValue(capture.mapNotNull {ingredient -> ingredient.toRecipeInstructions() })
                }
                database.getIngredientsForRecipe(id).collect{
                    val capture = it
                    _ingredients.postValue(capture.mapNotNull { it.toRecipeIngredient(id) })
                }
            }else{
                fetchRecipeInformationById(id)
                fetchRecipeIngredientsByRecipeId(id)
            }
        }
    }
    private fun fetchRecipeInformationById(id: Int){
        viewModelScope.launch {
            _instructions.postValue(api.getRecipeInstructionsById(id))
        }
    }
    private fun fetchRecipeIngredientsByRecipeId(id: Int){
        viewModelScope.launch {
            _ingredients.postValue(api.getIngredientsFromRecipeId(id))
        }
    }
}