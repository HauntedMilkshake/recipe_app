package com.example.recipe_app.api

import com.example.recipe_app.data.BareRecipe
import com.example.recipe_app.data.RecipeResponse
import com.example.recipe_app.utils.RecipeAdapter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitRecipeApiService: RecipeApiService {
    companion object {
        const val API_KEY = "fe492119566446658e59d2c5d43876ef"
        const val API_HOST = "https://api.spoonacular.com/recipes/"

        private var apiSingleton: RetrofitRecipeApiService? = null
        private var adapter = RecipeAdapter()
        fun getApi() = apiSingleton ?: RetrofitRecipeApiService()
    }

    private val recipeApi: RecipeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        recipeApi = retrofit.create()
    }

    override suspend fun getRecipesByComplexSearch(query: String): List<BareRecipe> = recipeApi.getRecipes(API_KEY, query).results.mapNotNull { adapter.adapt(it!!) }
    override suspend fun getRandomRecipe(): BareRecipe? = recipeApi.getRandomRecipe(API_KEY).results.first()?.let { adapter.adapt(it) }
}

interface RecipeApi {
    @GET("complexSearch")
    suspend fun getRecipes(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 1): RecipeResponse
    @GET("random")
    suspend fun getRandomRecipe(@Query("apiKey") apiKey: String, @Query("number") number: Int = 1): RecipeResponse
}
