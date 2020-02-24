package com.shanemaglangit.intervalalarm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlarmDatabaseDao {
    @Insert
    fun insert(alarm: Alarm) : Long

    @Query("SELECT * FROM alarm_table")
    fun getAllAlarm() : List<Alarm>
}