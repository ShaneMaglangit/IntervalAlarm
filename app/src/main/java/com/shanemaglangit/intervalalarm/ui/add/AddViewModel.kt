package com.shanemaglangit.intervalalarm.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shanemaglangit.intervalalarm.data.Alarm
import com.shanemaglangit.intervalalarm.data.AlarmDatabaseDao
import com.shanemaglangit.intervalalarm.ui.add.AddFragment.Companion.END_TIME
import com.shanemaglangit.intervalalarm.ui.add.AddFragment.Companion.START_TIME
import kotlinx.coroutines.*
import java.time.LocalTime

class AddViewModel(private val databaseDao: AlarmDatabaseDao) : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

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
    val interval = MutableLiveData(5)

    private val _startTimePicker = MutableLiveData<Boolean>()
    val startTimePicker: LiveData<Boolean>
        get() = _startTimePicker

    private val _endTimePicker = MutableLiveData<Boolean>()
    val endTimePicker: LiveData<Boolean>
        get() = _endTimePicker

    private val _toAlarmFragment = MutableLiveData<Boolean>()
    val toAlarmFragment: LiveData<Boolean>
        get() = _toAlarmFragment

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

    fun addAlarm() {
        uiScope.launch {
            val listOfDays = mutableListOf<String>()
            if(sunday.value == true) listOfDays.add("Sunday")
            if(monday.value == true) listOfDays.add("Monday")
            if(tuesday.value == true) listOfDays.add("Tuesday")
            if(wednesday.value == true) listOfDays.add("Wednesday")
            if(thursday.value == true) listOfDays.add("Thursday")
            if(friday.value == true) listOfDays.add("Friday")
            if(saturday.value == true) listOfDays.add("Saturday")
            val alarm = Alarm(days = listOfDays.toList(), startTime = startTime.value!!, endTime = endTime.value!!, interval = interval.value!!)
            insert(alarm)
            navigateToFragment()
        }
    }

    fun navigateToFragment() {
        _toAlarmFragment.value = true
    }

    fun navigateToFragmentComplete() {
        _toAlarmFragment.value = false
    }

    private suspend fun insert(alarm: Alarm) {
        withContext(Dispatchers.IO) {
            databaseDao.insert(alarm)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}