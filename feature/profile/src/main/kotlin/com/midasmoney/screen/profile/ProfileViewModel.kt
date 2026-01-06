package com.midasmoney.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProfileViewModel {

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile content"
    }
    val text: LiveData<String> = _text
}