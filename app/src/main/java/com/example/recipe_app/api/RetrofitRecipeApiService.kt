package com.example.recipe_app.api

import com.example.recipe_app.data.BareRecipe
import com.example.recipe_app.data.DomainRecipe
import com.example.recipe_app.utils.RecipeAdapter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitRecipeApiService: RecipeApiService {
    companion object {
        const val API_KEY = "HEHEHENONONO"
        const val API_HOST = "https://api.spoonacular.com/recipes"

        private var apiSingleton: RetrofitRecipeApiService? = null
        private var adapter = RecipeAdapter()
        fun getApi() = apiSingleton ?: RetrofitRecipeApiService()
    }

    private val recipeApi: RecipeApi

    init {
        //val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        recipeApi = retrofit.create()
    }

    override suspend fun getRecipes() {
        //TODO("Not yet implemented")
    }

    override suspend fun getSingleRecipe(): BareRecipe? {
        val recipes = recipeApi.getRecipe(API_KEY)
        return if (recipes.isNotEmpty()) {
            adapter.adapt(recipes.first())
        } else {
            null
        }
    }
}



interface RecipeApi{
    @GET("complexSearch")
    suspend fun getRecipe(@Query("apiKey") apiKey: String): List<DomainRecipe>

//    suspend fun getRecipe(@Query("apiKey") apiKey: String, @Query("cuisine") query: String): List<DomainRecipe>
}