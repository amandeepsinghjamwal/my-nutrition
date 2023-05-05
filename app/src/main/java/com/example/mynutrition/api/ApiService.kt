package com.example.mynutrition.api

import com.example.mynutrition.api.models.ApiResponse
import com.example.mynutrition.api.models.Item
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


private const val BASE_URL = "https://api.calorieninjas.com/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("v1/nutrition/")
    fun getData(@Query("query") inputData: String,@Header("X-Api-Key") apiKey:String ):Call<ApiResponse>

}

object ApplicationApi{
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}