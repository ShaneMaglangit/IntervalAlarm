package com.shanemaglangit.intervalalarm.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shanemaglangit.intervalalarm.data.AlarmDatabaseDao

class AddViewModelFactory(private val database: AlarmDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}