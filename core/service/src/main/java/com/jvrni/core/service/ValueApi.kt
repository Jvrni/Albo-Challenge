package com.jvrni.core.service

import com.jvrni.core.service.models.ValueResponse
import retrofit2.http.GET

interface ValueApi {

    @GET("value")
    suspend fun getValue(): ValueResponse
}
