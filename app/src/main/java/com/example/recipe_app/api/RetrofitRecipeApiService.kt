package com.example.recipe_app.api

//import com.example.recipe_app.data.Response
import android.util.Log
import com.example.recipe_app.data.ApiRecipeResponse
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.AutoCompleteResultApiResponse
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.data.EnhancedRecipeResponse
import com.example.recipe_app.data.Instructions
import com.example.recipe_app.data.InstructionsResponse
import com.example.recipe_app.data.Recipe
import com.example.recipe_app.utils.AutoCompleteAdapter
import com.example.recipe_app.utils.EnhancedRecipeAdapter
import com.example.recipe_app.utils.InstructionsAdapter
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
        const val API_KEY = "f26558dd27724c46aa0b87505416b908"
        const val API_HOST = "https://api.spoonacular.com/recipes/"

        private var apiSingleton: RetrofitRecipeApiService? = null
        private var recipeAdapter = RecipeAdapter()
        private var searchAdapter = AutoCompleteAdapter()
        private var enhancedRecipeAdapter = EnhancedRecipeAdapter()
        private var instructionsAdapter = InstructionsAdapter()
//        private var nutrientAdapter = NutrientAdapter()
        fun getApi() = apiSingleton ?: RetrofitRecipeApiService()
    }

    private val recipeApi: RecipeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(
                JacksonConverterFactory.create(
                    ObjectMapper().enable(
                        DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY
                    )
                )
            )
            .build()

        recipeApi = retrofit.create()
    }

    override suspend fun getRecipesByComplexSearch(query: String) : List<Recipe> = recipeApi.getRecipesByComplexSearch(API_KEY, query).results.mapNotNull { recipeAdapter.adapt(it!!) }

    override suspend fun getRandomRecipe(): EnhancedRecipe? = enhancedRecipeAdapter.adapt(recipeApi.getRandomRecipe(API_KEY).first().recipes!!.first()).also { Log.d("RANDOMRECIPE", it.toString()) }

    override suspend fun getAutoComplete(query: String): List<AutoCompleteResult> = recipeApi.getAutoCompleteSearchSuggestions(API_KEY, query).mapNotNull { searchAdapter.adapt(it) }

    override suspend fun getRecipeById(id: Int): EnhancedRecipe? = enhancedRecipeAdapter.adapt(recipeApi.getRecipeById(id, API_KEY))

    override suspend fun getRecipeInstructionsById(id: Int): List<Instructions> = recipeApi.getRecipeInstructionsByRecipeId(id, API_KEY).first().instructions!!.mapNotNull { instructionsAdapter.adapt(it) }
    override suspend fun getRecipeByIngredientSearch(query: String): List<Recipe> = recipeApi.getRecipesByIngredients(API_KEY, query).results.mapNotNull { recipeAdapter.adapt(it!!) }
    override suspend fun getRecipeByNutrientSearch(query: String): List<Recipe> = recipeApi.getRecipesByNutrientSearch(API_KEY, query).results.mapNotNull { recipeAdapter.adapt(it!!) }
}

interface RecipeApi {
    @GET("complexSearch")
    suspend fun getRecipesByComplexSearch(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 6): ApiRecipeResponse
    @GET("findByNutrients")
    suspend fun getRecipesByNutrientSearch(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 6): ApiRecipeResponse
    @GET("findByIngredients")
    suspend fun getRecipesByIngredients(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 6): ApiRecipeResponse

    @GET("random")
    suspend fun getRandomRecipe(@Query("apiKey") apiKey: String, @Query("number") number: Int = 1): List<EnhancedRecipeResponse>
    @GET("autocomplete")
    suspend fun getAutoCompleteSearchSuggestions(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 5): List<AutoCompleteResultApiResponse>
    @GET("{id}/information")
    suspend fun getRecipeById(@Path("id") id: Int, @Query("apiKey") apiKey: String): EnhancedRecipeResponse.EnhancedRecipe

    @GET("{id}/analyzedInstructions")
    suspend fun getRecipeInstructionsByRecipeId(@Path("id") id: Int, @Query("apiKey") apiKey: String): List<InstructionsResponse>


//    @GET("{id}/nutritionWidget.json")

//    suspend fun getRecipeNutritionById(@Path("id") id: Int, @Query("apiKey") apiKey: String):

}
