package com.example.mynutrition.repository

import android.util.Log
import com.example.mynutrition.api.ApiKey
import com.example.mynutrition.api.ApplicationApi
import com.example.mynutrition.api.models.ApiResponse
import com.example.mynutrition.data.DataSource
import com.example.mynutrition.data.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(): DataSource {
    private val key = ApiKey.Key
    override fun callApi(foodItemName: String?): Flow<ResponseResult> {
        return callbackFlow {
            val req = ApplicationApi.retrofitService.getData(foodItemName!!, key)
            req.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.code() == 200) {
                        this@callbackFlow.trySend(ResponseResult.Success(response.body()!!.items))
                    }
                    Log.e("data", response.code().toString())
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("failure", "failure")
                    this@callbackFlow.trySend(ResponseResult.Error(t.toString()))
                }
            })
            awaitClose { req.cancel() }
        }.flowOn(Dispatchers.IO)
    }
}