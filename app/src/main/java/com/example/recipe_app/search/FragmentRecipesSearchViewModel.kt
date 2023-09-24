package com.example.recipe_app.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.Recipe
import com.example.recipe_app.getApiService
import com.example.recipe_app.getRecipeDatabase
import com.example.recipe_app.search.mode.SearchMode
import com.example.recipe_app.utils.toRoomIngredient
import com.example.recipe_app.utils.toRoomRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentRecipesSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val api = application.getApiService()
    private val database = application.getRecipeDatabase()
    private val _autoCompleteText = MutableLiveData<List<AutoCompleteResult>>()
    val autoCompleteText: LiveData<List<AutoCompleteResult>> get() = _autoCompleteText

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _searchMode = MutableLiveData<SearchMode>(SearchMode.COMPLEXSEARCH)
    val searchMode: LiveData<SearchMode> get() = _searchMode

    private var carbRange: Pair<Int, Int> = Pair(10, 100)
    private var proteinRange: Pair<Int, Int> = Pair(10, 100)
    private var fatRange: Pair<Int, Int> = Pair(10, 100)

    fun getCarbRange() = carbRange
    fun setCarbRange(min: Int, max: Int) {
        carbRange = Pair(min, max)
    }

    fun getProteinRange() = proteinRange
    fun setProteinRange(min: Int, max: Int) {
        proteinRange = Pair(min, max)
    }

    fun getFatRange() = fatRange
    fun setFatRange(min: Int, max: Int) {
        fatRange = Pair(min, max)
    }



    fun fetchAutoCompleteText(query: String) {
        viewModelScope.launch {
            _autoCompleteText.postValue(api.getAutoComplete(query))
        }
    }

    fun fetchRecipes(query: String, minCarbs: Int, maxCarbs: Int, minProtein: Int, maxProtein: Int, minFat: Int, maxFat: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (searchMode.value) {
                SearchMode.SEARCHBYINGREDIENTS -> _recipes.postValue(api.getRecipeByIngredientSearch(query))
                SearchMode.SEARCHBYNUTRIENTS -> _recipes.postValue(
                    api.getRecipeByNutrientSearch(minCarbs, maxCarbs, minProtein, maxProtein, minFat, maxFat))
                else -> _recipes.postValue(api.getRecipesByComplexSearch(query))
            }
        }
    }
    fun favouriteRecipe(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(true)
                val dbRecipe = api.getRecipeById(recipeId)!!.toRoomRecipe()
                database.insertRecipe(dbRecipe)
                val dbIngredients = api.getIngredientsFromRecipeId(recipeId)
                database.insertIngredients(dbIngredients.mapNotNull { it.toRoomIngredient(dbRecipe.id) })
        }
    }
    fun unfavouriteRecipe(recipeId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(false)
            database.deleteRecipeById(recipeId)
            database.deleteIngredientByRecipeId(recipeId)
        }
    }
    fun addSearchMode(mode: SearchMode){
        _searchMode.value = mode
    }
}
