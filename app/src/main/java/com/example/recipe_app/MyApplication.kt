package com.example.recipe_app

import android.app.ActivityManager
import android.app.Application
import android.util.Log
import com.example.recipe_app.api.RetrofitRecipeApiService
import com.example.recipe_app.api.database.DaoProvider
import com.example.recipe_app.api.database.RecipeDao


class MyApplication: Application() {

    val apiService: RetrofitRecipeApiService by lazy {
        RetrofitRecipeApiService.getApi()
    }
//    val htmlApiService: HtmlRetrofitRecipeApiService
//        get() = HtmlRetrofitRecipeApiService()
    lateinit var recipeDatabase: RecipeDao

    override fun onCreate() {
        super.onCreate()
        Log.d("are you a monkey?",  ActivityManager.isUserAMonkey().toString())
        recipeDatabase = DaoProvider.getRecipeDao(applicationContext)

    }
}

fun Application.getApiService() = (this as MyApplication).apiService
fun Application.getRecipeDatabase() = (this as MyApplication).recipeDatabase
//fun Application.getHtmlRecipeApi() = (this as MyApplication).htmlApiService
