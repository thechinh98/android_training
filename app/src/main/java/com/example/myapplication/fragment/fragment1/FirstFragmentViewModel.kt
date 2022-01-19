package com.example.myapplication.fragment.fragment1

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstFragmentViewModel : ViewModel(){
    private var _stringFirst = MutableLiveData<String>()
    val stringFirst : MutableLiveData<String> get() = _stringFirst

    private val _stringSecond = MutableLiveData<String>()
    val stringSecond : MutableLiveData<String> get() = _stringSecond

    fun changeTextFirst(e: Editable?){
        _stringFirst.postValue(e.toString())
    }

    fun onClick(){
        _stringFirst.value = "abc"
    }
}