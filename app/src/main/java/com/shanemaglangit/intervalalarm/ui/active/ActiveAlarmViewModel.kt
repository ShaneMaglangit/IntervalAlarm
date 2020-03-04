package com.shanemaglangit.intervalalarm.ui.active

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shanemaglangit.intervalalarm.R

class ActiveAlarmViewModel(application: Application) : AndroidViewModel(application) {

    private val _isDone = MutableLiveData<Boolean>()
    val isDone: LiveData<Boolean>
        get() = _isDone

    private val timer: CountDownTimer
    private val mediaPlayer = MediaPlayer.create(application, R.raw.default_ringtone)
    private val vibrator = getApplication<Application>().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    init {
        timer = object: CountDownTimer(60000, 60000) {
            override fun onFinish() { dismissAlarm() }
            override fun onTick(millisUntilFinished: Long) { }
        }

        playRingtoneAndVibrate()
        timer.start()
    }

    fun dismissAlarm() {
        _isDone.value = true
        timer.cancel()
    }

    private fun playRingtoneAndVibrate() {
        mediaPlayer.start()

        if (Build.VERSION.SDK_INT >= 26)
            vibrator.vibrate(VibrationEffect.createOneShot(60000, VibrationEffect.DEFAULT_AMPLITUDE))
        else vibrator.vibrate(60000)
    }
}