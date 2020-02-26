package com.shanemaglangit.intervalalarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="alarm_table")
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var days: List<String> = listOf(),
    var startTime: Long,
    var endTime: Long,
    var interval: Int = 300000,
    var snooze: Boolean = false,
    var vibrate: Boolean = false
)