package com.example.recipe_app.instructions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.Ingredients
import com.example.recipe_app.data.Instructions
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class FragmentInstructionsViewModel(application: Application): AndroidViewModel(application) {
    private val api = application.getApiService()
    private val _instructions = MutableLiveData<List<Instructions>>()
    val instructions: MutableLiveData<List<Instructions>> get() = _instructions

    private val _ingredients = MutableLiveData<List<Ingredients.Ingredient>>()
    val ingredients: LiveData<List<Ingredients.Ingredient>> get() = _ingredients

    fun fetchRecipeInformationById(id: Int){
        viewModelScope.launch {
            _instructions.postValue(api.getRecipeInstructionsById(id))
        }
    }
    fun fetchRecipeIngredientsByRecipeId(id: Int){
        viewModelScope.launch {
            _ingredients.postValue(api.getIngredientsFromRecipeId(id))
        }
    }
}