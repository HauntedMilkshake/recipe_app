package com.example.recipe_app.api

import android.util.Log
import com.example.recipe_app.data.AnalyzedRecipe
import com.example.recipe_app.data.ApiRecipeResponse
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.AutoCompleteResultApiResponse
import com.example.recipe_app.data.RecipeResponse
import com.example.recipe_app.data.Response
//import com.example.recipe_app.data.Response
import com.example.recipe_app.utils.AnalyzedRecipeAdapter
import com.example.recipe_app.utils.AutoCompleteAdapter
import com.example.recipe_app.utils.NutrientAdapter
import com.example.recipe_app.utils.RecipeAdapter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RetrofitRecipeApiService: RecipeApiService {
    companion object {
        const val API_KEY = "fe492119566446658e59d2c5d43876ef"
        const val API_HOST = "https://api.spoonacular.com/recipes/"

        private var apiSingleton: RetrofitRecipeApiService? = null
        private var recipeAdapter = RecipeAdapter()
        private var searchAdapter = AutoCompleteAdapter()
        private var analyzedRecipeAdapter = AnalyzedRecipeAdapter()
        private var nutrientAdapter = NutrientAdapter()
        fun getApi() = apiSingleton ?: RetrofitRecipeApiService()
    }

    private val recipeApi: RecipeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)))
            .build()

        recipeApi = retrofit.create()
    }

    override suspend fun getRecipesByComplexSearch(query: String): List<RecipeResponse> = recipeApi.getRecipesByComplexSearch(API_KEY, query).results.mapNotNull { recipe ->  recipeAdapter.adapt(recipe!!) }
    override suspend fun getRandomRecipe(): AnalyzedRecipe? = analyzedRecipeAdapter.adapt(recipeApi.getRandomRecipe(API_KEY).responses!!.first().also{ Log.d("RANDOMRECIPE", it.toString())})
    override suspend fun getAutoComplete(query: String): List<AutoCompleteResult> = recipeApi.getAutoCompleteSearchSuggestions(API_KEY, query).mapNotNull { searchAdapter.adapt(it) }
   // override suspend fun getRecipeById(id: Int) {
        //TODO()
    //}
    //: AnalyzedRecipe? = analyzedRecipeAdapter.adapt(recipeApi.getRecipeById(id, API_KEY))
}

interface RecipeApi {
    @GET("complexSearch")
    suspend fun getRecipesByComplexSearch(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 6): ApiRecipeResponse
    @GET("random")
    suspend fun getRandomRecipe(@Query("apiKey") apiKey: String, @Query("number") number: Int = 1): Response
    @GET("autocomplete")
    suspend fun getAutoCompleteSearchSuggestions(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 5): List<AutoCompleteResultApiResponse>
    @GET("{id}/information")
    suspend fun getRecipeById(@Path("id") id: Int, @Query("apiKey") apiKey: String): Response

//    @GET("{id}/nutritionWidget.json")
//    suspend fun getRecipeNutritionById(@Path("id") id: Int, @Query("apiKey") apiKey: String):

}
