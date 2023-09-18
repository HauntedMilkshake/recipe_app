package com.example.recipe_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.api.RetrofitRecipeApiService
import com.example.recipe_app.data.BareRecipe
import kotlinx.coroutines.launch

class FragmentHomeViewModel(application: Application): AndroidViewModel(application) {
    private var api: RetrofitRecipeApiService = application.getApiService()
    private val _recipe = MutableLiveData<BareRecipe>()
    val recipe: LiveData<BareRecipe> get() = _recipe
    init {
        fetchRecipe()
    }

    private fun fetchRecipe() {
        viewModelScope.launch {
            val result = api.getRecipesByComplexSearch("pasta").first()
            _recipe.postValue(result ?: BareRecipe(id=-1, title="Error Recipe", imageUrl="noImage.png"))
        }
    }
}