package com.net.webhttp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel: ViewModel() {
    val numberA :MutableLiveData<String> by lazy { MutableLiveData() }
    val numberB : MutableLiveData<String> by lazy { MutableLiveData() }
    val result: MutableLiveData<String> by lazy { MutableLiveData() }
    val servletURL = "http://10.0.2.2:8080/CoroutineGson/GsonTest"

    suspend fun toWebCaculator() {
        var j = JsonObject()
        j.addProperty("a",numberA.value.toString().trim())
        j.addProperty("b",numberB.value.toString().trim())
        ( URL(servletURL).openConnection() as HttpURLConnection ).run {
            doInput = true
            doOutput = true
            requestMethod = "POST"
            useCaches = false
            setChunkedStreamingMode(0)
            setRequestProperty("charset","UTF-8")
            outputStream.bufferedWriter().use {
                it.write(j.toString())
            }
            if(responseCode == 200) {
                val backString = inputStream.bufferedReader().readLine()
                withContext(Dispatchers.Main) {
                    result.value = backString.trim()
                }
            }
        }
    }
}