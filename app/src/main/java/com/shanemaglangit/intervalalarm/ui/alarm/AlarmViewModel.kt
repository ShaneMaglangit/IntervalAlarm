package com.shanemaglangit.intervalalarm.ui.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shanemaglangit.intervalalarm.data.Alarm
import com.shanemaglangit.intervalalarm.data.AlarmDatabaseDao
import kotlinx.coroutines.*

class AlarmViewModel(private val databaseDao: AlarmDatabaseDao) : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _toAddFragment = MutableLiveData<Boolean>()
    val toAddFragment: LiveData<Boolean>
        get() = _toAddFragment

    private val _alarmList = MutableLiveData<List<Alarm>>()
    val alarmList: LiveData<List<Alarm>>
        get() = _alarmList

    init {
        uiScope.launch { _alarmList.value = getAllAlarm() }
    }

    fun removeAlarm(id: Long) {
        uiScope.launch {
            _alarmList.value = deleteAndGetAlarm(id)
        }
    }

    private suspend fun deleteAndGetAlarm(id: Long) = withContext(Dispatchers.IO) { databaseDao.deleteAndGetAlarms(id) }

    private suspend fun getAllAlarm() : List<Alarm> = withContext(Dispatchers.IO) { databaseDao.getAllAlarm() }

    fun navigateToFragment() {
        _toAddFragment.value = true
    }

    fun navigateToFragmentComplete() {
        _toAddFragment.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}