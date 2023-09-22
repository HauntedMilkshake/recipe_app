package com.example.recipe_app.trivia

import android.app.Application
import android.util.Log
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
            val triviaValue = api.getRandomFoodTrivia()
            _trivia.postValue(triviaValue)
            Log.d("FOOD IN SCOPE", triviaValue)
            val detectedFoodsValue = api.getFoodFromFoodDetect(triviaValue)
            _detectedFoods.postValue(detectedFoodsValue)
        }
    }
}
