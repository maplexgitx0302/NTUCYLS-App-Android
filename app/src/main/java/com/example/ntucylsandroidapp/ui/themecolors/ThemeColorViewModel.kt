package com.example.ntucylsandroidapp.ui.themecolors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeColorViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _text = MutableLiveData<String>().apply {
        value = "This is theme color Fragment"
    }
    val text: LiveData<String> = _text
}