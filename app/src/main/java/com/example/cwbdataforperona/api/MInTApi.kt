package com.example.cwbdataforperona.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MInTApi {

    @GET("/api/v1/rest/datastore/F-C0032-001")
    fun get36Hours(@QueryMap map: Map<String, String>): Call<ResponseBody>
}