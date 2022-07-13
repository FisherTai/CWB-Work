package com.example.cwbdataforperona.mint

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cwbdataforperona.MainActivity
import com.example.cwbdataforperona.local.SharedUtil
import io.reactivex.disposables.Disposable

class MinTViewMode(application: Application) : AndroidViewModel(application) {

    private val _mintlist = MutableLiveData<MutableList<MintPOJO>>()
    val mintlist: LiveData<MutableList<MintPOJO>>
        get() = _mintlist

    private val mintListDisposable: Disposable

    init {
        Log.i("MinTViewMode", "MinTViewMode created!")
        _mintlist.value = mutableListOf()
        mintListDisposable = MinTRepository.get36HoursDataRX().subscribe {
            _mintlist.value = it
        }
    }


    fun checkFirstOpen(): Boolean {
        if (SharedUtil.getFirstOpenValue(getApplication(), MainActivity.SHARED_IS_OPENED)) {
            SharedUtil.saveFirstOpenValue(getApplication(), MainActivity.SHARED_IS_OPENED, false)
            return true
        }
        return false
    }

    override fun onCleared() {
        _mintlist.value?.clear()
        if (!mintListDisposable.isDisposed) {
            mintListDisposable.dispose()
        }
        super.onCleared()
        Log.i("MinTViewMode", "MinTViewMode destroyed!")
    }

}