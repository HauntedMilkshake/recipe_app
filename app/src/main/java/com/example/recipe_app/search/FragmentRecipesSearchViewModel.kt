package com.example.recipe_app.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class FragmentRecipesSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val api = application.getApiService()
    private val _autoCompleteText = MutableLiveData<List<AutoCompleteResult>>()
    val autoCompleteText: LiveData<List<AutoCompleteResult>> get() = _autoCompleteText
    fun  fetchAutoCompleteText(query: String){
        viewModelScope.launch {
            _autoCompleteText.postValue(api.getAutoComplete(query))
            Log.d("autoComplete", "${_autoCompleteText.toString()}")
        }
    }

}