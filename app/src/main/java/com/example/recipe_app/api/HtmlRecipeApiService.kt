package com.example.recipe_app.api

interface HtmlRecipeApiService {
    suspend fun getVladiMirishe(id: Int): String
}