package com.example.recipe_app.api

//import com.example.recipe_app.data.Response
import IngredientResponse
import com.example.recipe_app.data.ApiRecipeResponse
import com.example.recipe_app.data.AutoCompleteResult
import com.example.recipe_app.data.AutoCompleteResultApiResponse
import com.example.recipe_app.data.EnhancedRecipe
import com.example.recipe_app.data.EnhancedRecipeResponse
import com.example.recipe_app.data.FoodDetect
import com.example.recipe_app.data.FoodDetectResponse
import com.example.recipe_app.data.Instructions
import com.example.recipe_app.data.InstructionsResponse
import com.example.recipe_app.data.RandomFoodTriviaResponse
import com.example.recipe_app.data.Recipe
import com.example.recipe_app.data.RecipeResponse
import com.example.recipe_app.utils.AutoCompleteAdapter
import com.example.recipe_app.utils.EnhancedRecipeAdapter
import com.example.recipe_app.utils.FoodDetectAdapter
import com.example.recipe_app.utils.InstructionsAdapter
import com.example.recipe_app.utils.RecipeAdapter
import com.example.recipe_app.utils.RecipeIngredientAdapter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

class RetrofitRecipeApiService: RecipeApiService {
    companion object {
        const val API_KEY = "7ee16282b85044379c10ed142eaf5ddd"
        const val API_HOST = "https://api.spoonacular.com/"

        private var apiSingleton: RetrofitRecipeApiService? = null
        private var recipeAdapter = RecipeAdapter()
        private var searchAdapter = AutoCompleteAdapter()
        private var enhancedRecipeAdapter = EnhancedRecipeAdapter()
        private var instructionsAdapter = InstructionsAdapter()
        private var foodDetectAdapter = FoodDetectAdapter()
        private var nestedNestAdapter = RecipeIngredientAdapter()
        fun getApi() = apiSingleton ?: RetrofitRecipeApiService()
    }
    private val recipeApi: RecipeApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY))).build()
        recipeApi = retrofit.create(RecipeApi::class.java)
    }
    override suspend fun getRecipesByComplexSearch(query: String) : List<Recipe> = recipeApi.getRecipesByComplexSearch(API_KEY, query).results.mapNotNull { recipeAdapter.adapt(it!!) }
    override suspend fun getRandomRecipe(): EnhancedRecipe? = enhancedRecipeAdapter.adapt(recipeApi.getRandomRecipe(API_KEY).first().recipes!!.first())
    override suspend fun getAutoComplete(query: String): List<AutoCompleteResult> = recipeApi.getAutoCompleteSearchSuggestions(API_KEY, query).mapNotNull { searchAdapter.adapt(it) }
    override suspend fun getRecipeById(id: Int): EnhancedRecipe? = enhancedRecipeAdapter.adapt(recipeApi.getRecipeById(id, API_KEY))
    override suspend fun getRecipeInstructionsById(id: Int): List<Instructions> = recipeApi.getRecipeInstructionsByRecipeId(id, API_KEY).first().instructions!!.mapNotNull { instructionsAdapter.adapt(it) }
    override suspend fun getRecipeByIngredientSearch(ingredient: String): List<Recipe> = recipeApi.getRecipesByIngredients(API_KEY, ingredient).mapNotNull { recipeAdapter.adapt(it) }
    override suspend fun getRecipeByNutrientSearch(minCarbs: Int, maxCarbs: Int, minProtein: Int, maxProtein: Int, minFat: Int, maxFat: Int): List<Recipe> = recipeApi.getRecipesByNutrientSearch(API_KEY, minCarbs, maxCarbs, minProtein, maxProtein, minFat, maxFat).mapNotNull { recipeAdapter.adapt(it) }
    override suspend fun getRandomFoodTrivia(): String = recipeApi.getRandomFoodTrivia(API_KEY).text ?: "Unfortunately, no trivia now :("
    override suspend fun getFoodFromFoodDetect(text: String): List<FoodDetect> = recipeApi.getDetectedFoods(text, API_KEY).annotations!!.mapNotNull { foodDetectAdapter.adapt(it) }
    override suspend fun getIngredientsFromRecipeId(recipeId: Int) = recipeApi.getIngredientsByRecipeId(recipeId, API_KEY).ingredients!!.mapNotNull { nestedNestAdapter.adapt(it) }
}
interface RecipeApi {
    @GET("recipes/complexSearch")
    suspend fun getRecipesByComplexSearch(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 6): ApiRecipeResponse
    //might not need the wrapper for ApiRecipeResponse but might need just a list of whats inside
    @GET("recipes/findByNutrients")
    suspend fun getRecipesByNutrientSearch(@Query("apiKey") apiKey: String,@Query("minCarbs") minCarbs: Int, @Query("maxCarbs") maxCarbs: Int, @Query("minProtein") minProtein: Int, @Query("maxProtein") maxProtein: Int, @Query("minFat") minFat: Int, @Query("maxFat") maxFat: Int, @Query("number") number: Int = 6): List<RecipeResponse>
    @GET("recipes/findByIngredients")
    suspend fun getRecipesByIngredients(@Query("apiKey") apiKey: String, @Query("ingredients") query: String, @Query("number") number: Int = 6): List<RecipeResponse>
    @GET("recipes/random")
    suspend fun getRandomRecipe(@Query("apiKey") apiKey: String, @Query("number") number: Int = 1): List<EnhancedRecipeResponse>
    @GET("recipes/autocomplete")
    suspend fun getAutoCompleteSearchSuggestions(@Query("apiKey") apiKey: String, @Query("query") query: String, @Query("number") number: Int = 5): List<AutoCompleteResultApiResponse>
    @GET("recipes/{id}/information")
    suspend fun getRecipeById(@Path("id") id: Int, @Query("apiKey") apiKey: String): EnhancedRecipeResponse.EnhancedRecipe
    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getRecipeInstructionsByRecipeId(@Path("id") id: Int, @Query("apiKey") apiKey: String): List<InstructionsResponse>
    @GET("food/trivia/random")
    suspend fun getRandomFoodTrivia(@Query("apiKey") apiKey: String): RandomFoodTriviaResponse
    @POST("food/detect")
    @FormUrlEncoded
    suspend fun getDetectedFoods(@Field("text") text: String, @Query("apiKey") apiKey: String): FoodDetectResponse
    @GET("recipes/{id}/ingredientWidget.json")
    suspend fun getIngredientsByRecipeId(@Path("id") id: Int, @Query("apiKey") apiKey: String): IngredientResponse
}


