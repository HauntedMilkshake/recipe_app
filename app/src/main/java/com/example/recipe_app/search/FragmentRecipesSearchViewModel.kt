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
//    private val database = application.getRecipeDatabase()
    private val _autoCompleteText = MutableLiveData<List<AutoCompleteResult>>()
    private val _recipes = MutableLiveData<List<Recipe>>()
    val autoCompleteText: LiveData<List<AutoCompleteResult>> get() = _autoCompleteText
    val recipes: LiveData<List<Recipe>> get() = _recipes
    private var searchMode: SearchMode = SearchMode.COMPLEXSEARCH

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

    fun getSearchMode() = searchMode

    fun setSearchMode(mode: SearchMode) {
        searchMode = mode
    }

    fun fetchAutoCompleteText(query: String) {
        viewModelScope.launch {
            _autoCompleteText.postValue(api.getAutoComplete(query))
        }
    }

    fun fetchRecipes(query: String, minCarbs: Int, maxCarbs: Int, minProtein: Int, maxProtein: Int, minFat: Int, maxFat: Int) {
        viewModelScope.launch {
            when (searchMode) {
                SearchMode.SEARCHBYINGREDIENTS -> _recipes.postValue(api.getRecipeByIngredientSearch(query))
                SearchMode.SEARCHBYNUTRIENTS -> _recipes.postValue(
                    api.getRecipeByNutrientSearch(minCarbs, maxCarbs, minProtein, maxProtein, minFat, maxFat))
                else -> _recipes.postValue(api.getRecipesByComplexSearch(query))
            }
        }
    }
//    fun favouriteRecipe(recipeId: Int){
//        viewModelScope.launch {
//            database.insertRecipe(convertToRoomRecipe(api.getRecipeById(recipeId)))
//        }
//    }
////most likely could have been handled better
//    private fun convertToRoomRecipe(recipe: EnhancedRecipe?): FavouriteRecipes = FavouriteRecipes(id = recipe!!.id, title = recipe.title, isVegetarian = recipe.isVegetarian, imageUrl = recipe.imageUrl, isVegan = recipe.isVegan, isGlutenFree = recipe.isGlutenFree, isDairyFree = recipe.isDairyFree, minutes = recipe.minutes, servings = recipe.servings, ingredients = convertEnhancedIngredientsToRoomIngredients(recipe.ingredients))
//    private fun convertEnhancedIngredientsToRoomIngredients(ingredients: List<EnhancedRecipe.Ingredient>): List<Ingredient>{
//        return ingredients.mapNotNull{
//            FavouriteRecipes.Ingredient(
//                name = it.name,
//                amount = it.amount,
//                unit = it.unit
//            )
//        }
//    }
}
