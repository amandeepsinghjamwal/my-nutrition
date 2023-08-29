package com.example.mynutrition.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mynutrition.api.models.Item
import com.example.mynutrition.fake.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var myViewModel: MainViewModel
    private lateinit var myRepo:FakeRepository

    @Before
    fun setupRepo(){
        myRepo=FakeRepository()
        myViewModel = MainViewModel(myRepo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun callApi_returnsSuccessData() {
        val item = listOf(
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
        )
        )
        Dispatchers.setMain(UnconfinedTestDispatcher())
        myViewModel.callApi("Test1")
        assertThat(myViewModel.responseData.getOrAwaitValue(), `is`(item))
    }

}