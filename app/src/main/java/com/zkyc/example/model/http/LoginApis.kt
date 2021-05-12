package com.zkyc.example.model.http

import com.google.gson.JsonObject
import com.zkyc.arms.model.bean.ApiResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApis {

    /**
     * 登录接口
     */
    @FormUrlEncoded
    @POST("m/moblies/logindo")
    fun login(@FieldMap map: Map<String, String>): Call<ApiResponse<JsonObject>>
}