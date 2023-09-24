package com.example.recipe_app.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HtmlRetrofitRecipeApiService : HtmlRecipeApiService {
    companion object {
        const val API_KEY = "7ee16282b85044379c10ed142eaf5ddd"
        const val API_HOST = "https://api.spoonacular.com/"
    }
    private val htmlRecipeApi: HtmlApiService
    init {
        val htmlRetrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        htmlRecipeApi = htmlRetrofit.create(HtmlApiService::class.java)
    }
    override suspend fun getVladiMirishe(id: Int): String {
        return suspendCoroutine { continuation ->
            val call: Call<String> = htmlRecipeApi.vladiMirishe(id, API_KEY)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val htmlContent = response.body()
                        continuation.resume(htmlContent!!)
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
interface HtmlApiService {
    @GET("recipes/{id}/tasteWidget")
    fun vladiMirishe(@Path("id") id: Int, @Query("apiKey") apiKey: String): Call<String>
}