package com.example.cwbdataforperona.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

object RetrofitClient {

    var BASE_URL = "https://opendata.cwb.gov.tw"

    val mInTApi: MInTApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MInTApi::class.java)
}

interface MInTApi {
    @GET("/api/v1/rest/datastore/F-C0032-001")
    fun get36HoursRX(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody?>>
}