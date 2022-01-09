package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val nameText = MutableLiveData<String>()
    val phoneText = MutableLiveData<String>()

    fun savePreviousValue(name: String, phone: String){
        nameText.value = name
        phoneText.value = phone
    }
}