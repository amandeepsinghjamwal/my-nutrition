package com.example.mynutrition.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mynutrition.api.models.Item
import com.example.mynutrition.data.ResponseResult
import com.example.mynutrition.fake.FakeRepository
import com.example.mynutrition.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainRepositoryTest(){
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var myViewModel: MainViewModel
    private lateinit var myRepo: FakeRepository

    @Before
    fun setupRepo(){
        myRepo= FakeRepository()
        myViewModel = MainViewModel(myRepo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun callApi_returnsSuccessData()= runTest{
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
        val search = myRepo.callApi("Test1").first()
        MatcherAssert.assertThat(ResponseResult.Success(item), Matchers.`is`(search))
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun callApi_returnsSuccessEmptyData()= runTest{
        val search = myRepo.callApi("Test3").first()
        MatcherAssert.assertThat(ResponseResult.Success(emptyList()), Matchers.`is`(search))
    }
}