package com.example.mynutrition.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynutrition.api.ApiKey
import com.example.mynutrition.api.ApplicationApi
import com.example.mynutrition.api.models.ApiResponse
import com.example.mynutrition.api.models.Item
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val key = ApiKey.Key
    private var _responseData = MutableLiveData<List<Item>>()
    val responseData: LiveData<List<Item>> get() = _responseData

    fun callApi(foodItemName: String?) = viewModelScope.launch {
        val callApi = ApplicationApi.retrofitService.getData(foodItemName!!, key)
        callApi.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.code() == 200) {
                    _responseData.value = response.body()!!.items
//                        binding.shimmer.visibility= View.GONE
//                        responseList= response.body()!!.items as MutableList<Item>
//                        adapter.submitList(responseList)
//                        if(responseList.isEmpty()){
//                            hideOverlays(0)
//                        }

                }
                Log.e("data", response.code().toString())
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("failure", "failure")
            }

        })
    }
}