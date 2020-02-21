package com.shanemaglangit.intervalalarm.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shanemaglangit.intervalalarm.ui.add.AddFragment.Companion.END_TIME
import com.shanemaglangit.intervalalarm.ui.add.AddFragment.Companion.START_TIME
import java.time.LocalTime

class AddViewModel : ViewModel() {
    val startTime = MutableLiveData<Long>()
    val endTime = MutableLiveData<Long>()
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

    fun changeEndTime() {
        _endTimePicker.value = true
    }

    fun setTime(timeInMillis: Long, type: Int) {
        if(startTime != null) {
            when(type) {
                START_TIME -> this.startTime.value = timeInMillis
                END_TIME -> this.endTime.value = timeInMillis
            }
        }
        _startTimePicker.value = false
    }
}