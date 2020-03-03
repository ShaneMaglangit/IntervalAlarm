package com.shanemaglangit.intervalalarm.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.shanemaglangit.intervalalarm.ActiveAlarmActivity
import com.shanemaglangit.intervalalarm.R
import com.shanemaglangit.intervalalarm.data.Alarm
import com.shanemaglangit.intervalalarm.data.AlarmDatabase
import com.shanemaglangit.intervalalarm.data.AlarmDatabaseDao
import kotlinx.coroutines.*
import timber.log.Timber

class AlarmService : LifecycleService() {
    private lateinit var timer: CountDownTimer
    private lateinit var alarmDatabaseDao: AlarmDatabaseDao

    private val alarms = MutableLiveData<List<Alarm>>()

    private lateinit var currentAlarm: Alarm
    private var alarmTime: Long = 0L

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    companion object {
        const val CHANNEL_ID = "INTERVAL_ALARM"
    }

    override fun onCreate() {
        super.onCreate()
        alarmDatabaseDao = AlarmDatabase.getInstance(this).alarmDao()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Alarm Interval")
            .setContentText("Click here to modify your preferences")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        alarms.observe(this, Observer {
            if(it.isNotEmpty()) {
                currentAlarm = it[0]
                createNotificationChannel()
                startForeground(1, notification)
                getNextAlarm()
            } else if(it.isEmpty()) {
                stopSelf()
            }
        })

        loadAlarms()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun loadAlarms() {
        uiScope.launch {
            alarms.value = getAllAlarm()
        }
    }

    private fun getNextAlarm() {
        alarmTime = currentAlarm.startTime
        while(alarmTime <= System.currentTimeMillis() || alarmTime - System.currentTimeMillis() < 60000) {
            alarmTime += currentAlarm.interval
            if(alarmTime > currentAlarm.endTime) {
                currentAlarm = alarms.value!![alarms.value!!.indexOf(currentAlarm) + 1]
                alarmTime = currentAlarm.startTime
            }
        }
        startTimer(alarmTime - System.currentTimeMillis())
    }

    private fun startTimer(alarmTime: Long) {
        timer = object: CountDownTimer(alarmTime, alarmTime) {
            override fun onFinish() {
                val intent = Intent(this@AlarmService, ActiveAlarmActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                getNextAlarm()
            }
            override fun onTick(millisUntilFinished: Long) {}
        }.start()
    }

    private suspend fun getAllAlarm() : List<Alarm> = withContext(Dispatchers.IO) { alarmDatabaseDao.getAllAlarm() }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        timer.cancel()
    }

    /**
     * Creates the notification channel for the foreground service
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 27) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Interval Alarm Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
