package com.poly.poly_sender_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val studentsAttributingEvent: MutableLiveData<Boolean> = MutableLiveData()

    fun getStudentsAttributingEvent(): LiveData<Boolean> {
        return studentsAttributingEvent
    }

    fun setStudentsAttributingEvent(boolean: Boolean) {
        studentsAttributingEvent.postValue(boolean)
    }
}