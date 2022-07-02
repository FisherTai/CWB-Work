package com.example.cwbdataforperona.mint

interface ReqCallBack<T> {

    fun onSuccess(result: T?)
    fun onFailure()
}