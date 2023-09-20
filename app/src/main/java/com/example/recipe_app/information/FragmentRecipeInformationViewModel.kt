package com.example.recipe_app.information

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.recipe_app.getApiService

class FragmentRecipeInformationViewModel(application: Application): AndroidViewModel(application) {
    private val api = application.getApiService()
    fun getRecipe(id: Int){

    }
}