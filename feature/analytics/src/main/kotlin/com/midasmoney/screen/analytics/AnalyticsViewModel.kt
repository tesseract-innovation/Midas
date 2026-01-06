package com.midasmoney.screen.analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AnalyticsViewModel {
    private val _text = MutableLiveData<String>().apply {
        value = "This is analytics content"
    }
    val text: LiveData<String> = _text
}