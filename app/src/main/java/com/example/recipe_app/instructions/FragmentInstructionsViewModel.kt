package com.example.recipe_app.instructions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.Instructions
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class FragmentInstructionsViewModel(application: Application): AndroidViewModel(application) {
    private val api = application.getApiService()
    private val _enhancedRecipe = MutableLiveData<List<Instructions>>()
    val enhancedRecipe: MutableLiveData<List<Instructions>> get() = _enhancedRecipe
    fun fetchRecipeInformationById(id: Int){
        viewModelScope.launch {
            _enhancedRecipe.postValue(api.getRecipeInstructionsById(id))
        }
    }
}