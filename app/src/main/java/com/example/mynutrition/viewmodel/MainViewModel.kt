package com.example.mynutrition.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mynutrition.api.models.Item
import com.example.mynutrition.data.DataSource
import com.example.mynutrition.data.ResponseResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DataSource) : ViewModel() {


    private var _responseData = MutableLiveData<List<Item>>()
    private var deviceConnectionJob: Job? = null
    val responseData: LiveData<List<Item>> get() = _responseData

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun callApi(foodItemName: String?) {
        _dataLoading.value = true
        viewModelScope.launch {
            deviceConnectionJob = dataRepository.callApi(foodItemName).listen()
        }
    }

    private fun Flow<ResponseResult>.listen(): Job {
        return onEach { responseResult ->
            when (responseResult) {
                is ResponseResult.Success -> {
                    _responseData.value = responseResult.data
                    _dataLoading.value = false
                    _isError.value=false
                }

                is ResponseResult.Error -> {
                    _responseData.value = emptyList()
                    _dataLoading.value = false
                    _isError.value=true
                }

                ResponseResult.Loading -> TODO()
            }
        }.launchIn(viewModelScope)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val dataRepository: DataSource
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MainViewModel(dataRepository) as T)
}