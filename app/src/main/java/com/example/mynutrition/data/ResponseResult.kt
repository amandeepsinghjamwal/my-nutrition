package com.example.mynutrition.data

import com.example.mynutrition.api.models.Item


sealed class ResponseResult{
    data class Success(val data : List<Item>):ResponseResult()
    data class Error(val msg:String):ResponseResult()
    object Loading:ResponseResult()
}
