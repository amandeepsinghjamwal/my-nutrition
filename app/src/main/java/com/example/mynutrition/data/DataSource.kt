package com.example.mynutrition.data

import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun callApi(foodItemName: String?): Flow<ResponseResult>
}