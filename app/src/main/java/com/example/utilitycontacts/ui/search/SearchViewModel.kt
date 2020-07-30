package com.example.utilitycontacts.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is search Fragment"
    }

    private val _locationList = MutableLiveData<List<String>>().apply {
        value = listOf(" - ")
    }

    private val _occupationList = MutableLiveData<List<String>>().apply {
        value = listOf(" - ")
    }

    val text: LiveData<String> = _text
    val locationList : LiveData<List<String>> = _locationList
    val occupationList : LiveData<List<String>> = _occupationList


}
