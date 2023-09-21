package com.example.recipe_app.information

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class FragmentRecipeInformationViewModel(application: Application): AndroidViewModel(application) {
    private val api = application.getApiService()
    private val _enhancedRecipe = MutableLiveData<EnhancedRecipe>()
    val enhancedRecipe: LiveData<EnhancedRecipe> get() = _enhancedRecipe
    fun getRecipe(id: Int){
        viewModelScope.launch {
            _enhancedRecipe.postValue(api.getRecipeById(id))
            Log.d("LiveData", enhancedRecipe.value.toString())
        }
    }
}