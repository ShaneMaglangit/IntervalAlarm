package com.shanemaglangit.intervalalarm.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalTime

class AddViewModel : ViewModel() {
    val startTime = MutableLiveData<LocalTime>()
    val endTime = MutableLiveData<LocalTime>()
    val sunday = MutableLiveData<Boolean>()
    val monday = MutableLiveData<Boolean>()
    val tuesday = MutableLiveData<Boolean>()
    val wednesday = MutableLiveData<Boolean>()
    val thursday = MutableLiveData<Boolean>()
    val friday = MutableLiveData<Boolean>()
    val saturday = MutableLiveData<Boolean>()
    val snooze = MutableLiveData<Boolean>()
    val vibrate = MutableLiveData<Boolean>()

    private val _startTimePicker = MutableLiveData<Boolean>()
    val startTimePicker: LiveData<Boolean>
        get() = _startTimePicker

    private val _endTimePicker = MutableLiveData<Boolean>()
    val endTimePicker: LiveData<Boolean>
        get() = _endTimePicker

    fun changeStartTime() {
        _startTimePicker.value = true
    }

    fun setStartTime(startTime: LocalTime?) {
        if(startTime != null) this.startTime.value = startTime
        _startTimePicker.value = false
    }
}