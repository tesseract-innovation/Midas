package com.midasmoney.screen.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GoalsViewModel {

    private val _text = MutableLiveData<String>().apply {
        value = "This is goals content"
    }
    val text: LiveData<String> = _text
}