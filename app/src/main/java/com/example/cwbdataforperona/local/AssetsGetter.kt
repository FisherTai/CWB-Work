package com.example.cwbdataforperona.local

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object AssetsGetter {

    private const val TAG = "AssetsGetter"

    fun getFromAssets(context:Context , fileName: String) :String {
        val sb = StringBuilder()
        var inputReader: InputStreamReader? = null
        var bufReader: BufferedReader? = null
        try {
            inputReader = InputStreamReader(context.resources.assets.open(fileName))
            bufReader = BufferedReader(inputReader)
            var line: String?
            while (bufReader.readLine().also { line = it } != null) {
                sb.append(line)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getFromAssets: ", e)
        } finally {
            try {
                inputReader?.close()
                bufReader?.close()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        }
        return sb.toString()
    }
}




