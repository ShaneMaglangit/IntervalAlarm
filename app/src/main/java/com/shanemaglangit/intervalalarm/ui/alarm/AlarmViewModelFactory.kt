package com.shanemaglangit.intervalalarm.ui.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shanemaglangit.intervalalarm.data.AlarmDatabaseDao
import com.shanemaglangit.intervalalarm.ui.alarm.AlarmViewModel

class AlarmViewModelFactory(private val database: AlarmDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            return AlarmViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}