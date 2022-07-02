package com.example.cwbdataforperona.api

import retrofit2.Retrofit

object RetrofitClient {

    var BASE_URL = "https://opendata.cwb.gov.tw"

    val mInTApi: MInTApi
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).build()
        mInTApi = retrofit.create(MInTApi::class.java)
    }

}