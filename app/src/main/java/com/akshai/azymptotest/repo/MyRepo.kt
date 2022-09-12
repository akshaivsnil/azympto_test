package com.akshai.azymptotest.repo

import com.akshai.azymptotest.network.ApiServices
import com.akshai.azymptotest.repo.entity.MockApiEntity
import retrofit2.Response
import javax.inject.Inject

class MyRepo @Inject constructor(private val apiServices: ApiServices) {
    suspend fun getMockApi(): Response<MockApiEntity> {
        return apiServices.getMockApi("1637320776228")
    }
}