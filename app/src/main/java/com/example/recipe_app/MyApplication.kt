package com.example.recipe_app

import android.app.ActivityManager
import android.app.Application
import android.util.Log
import com.example.recipe_app.api.RetrofitRecipeApiService

class MyApplication: Application() {

    val apiService: RetrofitRecipeApiService by lazy {
        RetrofitRecipeApiService.getApi()
    }
//    val recipeDatabase = DaoProvider.getRecipeDao(applicationContext)


    override fun onCreate() {
        super.onCreate()
        Log.d("are you a monkey?",  ActivityManager.isUserAMonkey().toString())
    }
}

fun Application.getApiService() = (this as MyApplication).apiService
//fun Application.getRecipeDatabase() = (this as MyApplication).recipeDatabase
