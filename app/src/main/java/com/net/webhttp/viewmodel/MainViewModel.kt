package com.net.webhttp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel: ViewModel() {
    val TAG = "TAG ${javaClass.simpleName}"
    val numberA :MutableLiveData<String> by lazy { MutableLiveData() }
    val numberB : MutableLiveData<String> by lazy { MutableLiveData() }
    val result: MutableLiveData<String> by lazy { MutableLiveData() }
    val servletURL = "http://10.0.2.2:8080/CoroutineGson/GsonTest"

    fun toWebCaculator() {
        Log.d(TAG,"onClick")
        var j = JsonObject()
        j.addProperty("a", numberA.value.toString().trim())
        j.addProperty("b", numberB.value.toString().trim())
        Log.d(TAG,"toWebCaculator :" + j.toString())
        viewModelScope.launch(Dispatchers.IO) {
            (URL(servletURL).openConnection() as HttpURLConnection).run {
                doInput = true
                doOutput = true
                requestMethod = "POST"
                useCaches = false
                setChunkedStreamingMode(0)
                setRequestProperty("charset", "UTF-8")
                Log.d(TAG, "viewModelScope")
                    outputStream.bufferedWriter().use {
                        it.write(j.toString())
                    }
                    if (responseCode == 200) {
                        val backString = inputStream.bufferedReader().readLine()
                        withContext(Dispatchers.Main){
                            result.value = backString.trim()
                        }
                    }
            }
        }
    }

}