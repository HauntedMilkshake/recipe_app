package com.example.recipe_app.api

import com.example.recipe_app.data.AnalyzedRecipeDomain
import com.example.recipe_app.data.AnalyzedRecipeResponse
import com.example.recipe_app.data.ApiRecipeResponse
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.BareRecipe
import com.example.recipe_app.data.DomainAutoComplete
import com.example.recipe_app.utils.AnalyzedRecipeAdapter
import com.example.recipe_app.utils.AutoCompleteAdapter
import com.example.recipe_app.utils.RecipeAdapter
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

    override suspend fun getRecipesByComplexSearch(query: String): List<BareRecipe> = recipeApi.getRecipes(API_KEY, query).results.mapNotNull { recipe ->  recipeAdapter.adapt(recipe!!) }
    override suspend fun getRandomRecipe(): BareRecipe? = recipeApi.getRandomRecipe(API_KEY).results.first()?.let {recipe -> recipeAdapter.adapt(recipe) }
    override suspend fun getAutoComplete(query: String): List<AutoCompleteResult> = recipeApi.getAutoCompleteSearch(API_KEY, query).mapNotNull { searchAdapter.adapt(it) }
    override suspend fun getRecipeById(id: Int) = analyzedRecipeAdapter.adapt(recipeApi.getRecipyById(API_KEY, id))
}

interface RecipeApi {
    @GET("complexSearch")
    suspend fun getRecipes(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 6): ApiRecipeResponse
    @GET("random")
    suspend fun getRandomRecipe(@Query("apiKey") apiKey: String, @Query("number") number: Int = 1): ApiRecipeResponse
    @GET("autocomplete")
    suspend fun getAutoCompleteSearch(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 5): List<DomainAutoComplete>
    @GET("{id}/information")
    suspend fun getRecipyById(@Query("apiKey") apiKet: String, @Path("id") id: Int): AnalyzedRecipeResponse

}
