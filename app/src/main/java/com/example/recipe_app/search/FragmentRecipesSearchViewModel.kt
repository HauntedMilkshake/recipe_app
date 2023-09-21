package com.example.recipe_app.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.Recipe
import com.example.recipe_app.getApiService
import com.example.recipe_app.search.mode.SearchMode
import kotlinx.coroutines.launch

class FragmentRecipesSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val api = application.getApiService()
    private val _autoCompleteText = MutableLiveData<List<AutoCompleteResult>>()
    private val _recipes = MutableLiveData<List<Recipe>>()
    val autoCompleteText: LiveData<List<AutoCompleteResult>> get() = _autoCompleteText
    val recipes: LiveData<List<Recipe>> get() = _recipes

    fun fetchAutoCompleteText(query: String){
        viewModelScope.launch {
            _autoCompleteText.postValue(api.getAutoComplete(query))
        }
    }

    fun fetchRecipes(query: String, searchMode: SearchMode){
        viewModelScope.launch {
            when(searchMode){
                SearchMode.SEARCHBYINGREDIENTS -> _recipes.postValue(api.getRecipeByIngredientSearch(query))
                SearchMode.SEARCHBYNUTRIENTS -> _recipes.postValue(api.getRecipeByNutrientSearch(query))
                else -> _recipes.postValue(api.getRecipesByComplexSearch(query))
            }
        }
    }

}