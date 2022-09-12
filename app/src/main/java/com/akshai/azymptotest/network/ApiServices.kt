package com.akshai.azymptotest.network

import com.akshai.azymptotest.repo.entity.MockApiEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("miscs/cats")
    suspend fun getMockApi(@Query("orgId") orgId: String): Response<MockApiEntity>

}

