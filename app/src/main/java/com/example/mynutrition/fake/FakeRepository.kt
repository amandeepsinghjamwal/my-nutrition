package com.example.mynutrition.fake

import com.example.mynutrition.api.models.Item
import com.example.mynutrition.data.DataSource
import com.example.mynutrition.data.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeRepository() : DataSource {
    private val foodList = listOf(
        Item(
            1.1,
            1.1,
            2,
            1.1,
            1.1,
            1.1,
            "Test1",
            12,
            1.1,
            1.1,
            12,
            1.1
        ), Item(
            1.1,
            1.1,
            2,
            1.1,
            1.1,
            1.1,
            "Test2",
            12,
            1.1,
            1.1,
            12,
            1.1
        )
    )

    override fun callApi(foodItemName: String?): Flow<ResponseResult> {
        return flow<ResponseResult> {
            val index = foodList.indexOfFirst {
                (it.name == foodItemName!!)
            }
            if(index!=-1){
                emit(ResponseResult.Success(listOf(foodList[index])))
            }
            else{
                emit(ResponseResult.Success(emptyList()))
            }
        }.flowOn(Dispatchers.IO)
    }
}