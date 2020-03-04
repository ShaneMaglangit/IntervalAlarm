package com.shanemaglangit.intervalalarm.ui.active

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActiveAlarmViewModel : ViewModel() {
    private val _isDone = MutableLiveData<Boolean>()
    val isDone: LiveData<Boolean>
        get() = _isDone

    init {
        object: CountDownTimer(60000, 60000) {
            override fun onFinish() { _isDone.value = true }
            override fun onTick(millisUntilFinished: Long) { }
        }.start()
    }
}