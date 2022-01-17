package com.example.myapplication.fragment.fragment1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstFragmentViewModel : ViewModel(){
    private var _stringFirst = MutableLiveData<String>()
    val stringFirst : MutableLiveData<String> get() = _stringFirst

    private val _stringSecond = MutableLiveData<String>()
    val stringSecond : MutableLiveData<String> get() = _stringSecond

    override fun onCleared() {
        super.onCleared()
    }
}