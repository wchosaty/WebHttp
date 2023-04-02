package com.net.webhttp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val numberA :MutableLiveData<String> by lazy { MutableLiveData() }
    val numberB : MutableLiveData<String> by lazy { MutableLiveData() }
    val result: MutableLiveData<String> by lazy { MutableLiveData() }
}