package com.example.recipe_app.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.api.RetrofitRecipeApiService
import com.example.recipe_app.data.AnalyzedRecipe
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class FragmentHomeViewModel(application: Application): AndroidViewModel(application) {
    private var api: RetrofitRecipeApiService = application.getApiService()
    private val _recipe = MutableLiveData<AnalyzedRecipe>()
    val recipe: LiveData<AnalyzedRecipe> get() = _recipe
    init {
        fetchRecipe()
    }

    private fun fetchRecipe() {
        viewModelScope.launch {
            val recipe = api.getRandomRecipe()
            _recipe.postValue(api.getRandomRecipe())
            Log.d("enhancedRecipe", "${_recipe.value}")
        }
    }
}