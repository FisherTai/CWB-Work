package com.example.cwbdataforperona.mint

import com.example.cwbdataforperona.api.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

object MinTRepository {

    fun get36HoursDataRX(): Observable<MutableList<MintPOJO>> {
        val queryMap: MutableMap<String, String> = mutableMapOf()
        queryMap["Authorization"] = "CWB-1D8CCDCC-1605-4CD1-9C63-9519FD99EEA5"
        queryMap["format"] = "JSON"
        queryMap["locationName"] = "臺北市"

        val observable: Observable<Response<ResponseBody?>> =
            RetrofitClient.mInTApi.get36HoursRX(queryMap)

        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val result = it.body()?.string()
                if (result == null)
                    mutableListOf() else parse36HoursList(result)
            }
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