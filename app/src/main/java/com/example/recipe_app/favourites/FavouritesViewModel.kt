//package com.example.recipe_app.favourites
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.example.recipe_app.api.database.FavouriteRecipes
//import com.example.recipe_app.getRecipeDatabase
//import kotlinx.coroutines.launch
//
//class FavouritesViewModel(application: Application): AndroidViewModel(application) {
////    private val api = application.getApiService()
//    private val database = application.getRecipeDatabase()
//    private val _favouriteRecipes = MutableLiveData<List<FavouriteRecipes>>()
//    val favouriteRecipes: LiveData<List<FavouriteRecipes>> get() = _favouriteRecipes
//    init{
//        getRecipesFromRoom()
//    }
//    private fun getRecipesFromRoom(){
//        viewModelScope.launch{
//            _favouriteRecipes.postValue(database.getAllFavoriteRecipes())
//        }
//    }
//    fun removeRecipeFromRoom(id: Int){
//        viewModelScope.launch {
//            database.deleteRecipeById(id)
//        }
//    }
//}
//
//
