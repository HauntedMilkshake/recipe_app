package com.example.recipe_app.trivia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.FoodDetect
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class FragmentRandomFoodFactsViewModel(application: Application) : AndroidViewModel(application) {
    private val api = application.getApiService()
    private val _trivia = MutableLiveData<String>()
    val trivia: LiveData<String> get() = _trivia
    private val _detectedFoods = MutableLiveData<List<FoodDetect>>()
    val detectedFoods: LiveData<List<FoodDetect>> get() = _detectedFoods

    init {
        fetchTriviaAndDetectedFoods()
    }

    fun fetchTriviaAndDetectedFoods() {
        viewModelScope.launch {
            val trivia = api.getRandomFoodTrivia()
            _trivia.postValue(trivia)
            _detectedFoods.postValue(api.getFoodFromFoodDetect(trivia))
        }
    }
}
