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

    private val _loadingComplete = MutableLiveData<Boolean>()
    val loadingComplete: LiveData<Boolean>
        get() = _loadingComplete

    private val _showToast = MutableLiveData<Boolean>()
    val showToast: LiveData<Boolean>
        get() = _showToast

    private lateinit var mintListDisposable: Disposable

    init {
        Log.i("MinTViewMode", "MinTViewMode created!")
        checkShowToast()
        get36HoursData()
    }

    private fun get36HoursData() {
        _loadingComplete.value = false
        mintListDisposable = MinTRepository.get36HoursDataRX().subscribe {
            Log.i("MinTViewMode", "subscribe!")
            _mintlist.value = it
            _loadingComplete.value = true
        }
    }


    private fun checkShowToast() {
        if (SharedUtil.getFirstOpenValue(getApplication(), MainActivity.SHARED_IS_OPENED)) {
            SharedUtil.saveFirstOpenValue(getApplication(), MainActivity.SHARED_IS_OPENED, false)
            _showToast.value = false
        } else {
            _showToast.value = true
        }
    }

    fun showToastComplete() {
        _showToast.value = false
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