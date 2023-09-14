package com.example.recipe_app.api

import com.example.recipe_app.data.DomainRecipe
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitRecipeApiService: RecipeApiService {
    companion object{
        const val API_KEY = ""
        const val API_HOST = "https://api.spoonacular.com/recipes/"
    }
    private val recipeApi: RecipeApi

    init{
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        recipeApi = retrofit.create()
    }
    override suspend fun getRecipes() {
        //TODO("Not yet implemented")
    }

    override suspend fun getSingleRecipe(query: String) = recipeApi.getRecipe(API_KEY, query)

}

interface RecipeApi{
    @GET("complexSearch")
    suspend fun getRecipe(@Query("apiKey") apiKey: String, @Query("query") query: String): DomainRecipe
}