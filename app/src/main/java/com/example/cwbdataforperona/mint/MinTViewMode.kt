package com.example.cwbdataforperona.mint

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cwbdataforperona.MainActivity
import com.example.cwbdataforperona.local.SharedUtil

class MinTViewMode(application: Application) : AndroidViewModel(application) {

    private val _mintlist = MutableLiveData<MutableList<MintPOJO>>()
    val mintlist: LiveData<MutableList<MintPOJO>>
        get() = _mintlist

    init {
        Log.i("MinTViewMode", "MinTViewMode created!")
        _mintlist.value = mutableListOf()
        get36Hours()
    }


    fun checkFirstOpen(): Boolean {
        if (SharedUtil.getFirstOpenValue(getApplication(), MainActivity.SHARED_IS_OPENED)) {
            SharedUtil.saveFirstOpenValue(getApplication(), MainActivity.SHARED_IS_OPENED, false)
            return true
        }
        return false
    }

     fun get36Hours() {
        MinTRepository.get36HoursData(object : ReqCallBack<MutableList<MintPOJO>> {
            override fun onSuccess(result: MutableList<MintPOJO>?) {
                _mintlist.value = result
            }

            override fun onFailure() {

            }
        })
//         _mintlist.value = MinTRepository.getFakeList(getApplication())
    }

    override fun onCleared() {
        _mintlist.value?.clear()
        super.onCleared()
        Log.i("MinTViewMode", "MinTViewMode destroyed!")
    }

}