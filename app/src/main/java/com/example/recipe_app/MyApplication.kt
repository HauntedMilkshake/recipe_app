package com.example.recipe_app

import android.app.Application
import android.util.Log
import com.example.recipe_app.api.RetrofitRecipeApiService

class MyApplication: Application() {

    val apiService: RetrofitRecipeApiService by lazy {
        RetrofitRecipeApiService.getApi()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("hello", "MESSAGE")
    }
}

fun Application.getApiService() = (this as MyApplication).apiService
