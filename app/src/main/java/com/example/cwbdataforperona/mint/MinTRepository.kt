package com.example.cwbdataforperona.mint

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.cwbdataforperona.api.RetrofitClient
import com.example.cwbdataforperona.local.AssetsGetter
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MinTRepository {

    fun getFakeList(context: Context): MutableList<MintPOJO> {
        val jsonStr = AssetsGetter.getFromAssets(context, "fakeJson.json")
        return if (TextUtils.isEmpty(jsonStr)) mutableListOf() else parse36HoursList(jsonStr)
    }

    fun get36HoursData(callbacl: ReqCallBack<MutableList<MintPOJO>>) {
        val queryMap : MutableMap<String,String> = mutableMapOf()
        queryMap.put("Authorization","CWB-1D8CCDCC-1605-4CD1-9C63-9519FD99EEA5")
        queryMap.put("format","JSON")
        queryMap.put("locationName","臺北市")

        val call: Call<ResponseBody> = RetrofitClient.mInTApi.get36Hours(queryMap)

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                Log.d("onResponse:", "get36HoursData")
                val result = response.body()?.string()
                if (result != null) {
                    callbacl.onSuccess(parse36HoursList(result))
                } else {
                    callbacl.onFailure()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                Log.e("onFailure:", "error:", t)
                callbacl.onFailure()
            }
        })
    }


    private fun parse36HoursList(jsonStr: String): MutableList<MintPOJO> {
        val dataList: MutableList<MintPOJO> = mutableListOf()
        val jsonObject = JSONObject(jsonStr)
        val records = jsonObject.optJSONObject("records")
        val location = records?.optJSONArray("location")
        val locationItem = location?.optJSONObject(0)
        val weatherElement = locationItem?.getJSONArray("weatherElement") ?: return dataList
        var minT: JSONObject? = null

        for (i in 0 until weatherElement.length()) {
            val data = weatherElement.optJSONObject(i)
            if ("MinT" == data?.optString("elementName")) {
                minT = data
                break
            }
        }
        val time: JSONArray = minT?.optJSONArray("time") ?: return dataList

        for (i in 0 until time.length()) {
            val data = time.optJSONObject(i)
            val parameter = data.optJSONObject("parameter")
            val dataBean = MintPOJO()
            if (data != null) {
                dataBean.startTime = data.getString("startTime")
                dataBean.endTime = data.getString("endTime")
            }
            if (parameter != null) {
                dataBean.parameterName = parameter.getString("parameterName")
                dataBean.parameterUnit = parameter.getString("parameterUnit")
            }
            if ((i + 1) % 3 == 0) {
                dataList.add(MintPOJO(true))
            }
            dataList.add(dataBean)
        }
        return dataList
    }

}