package com.shanemaglangit.intervalalarm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AlarmDatabaseDao {
    @Insert
    fun insert(alarm: Alarm) : Long

    @Query("SELECT * FROM alarm_table WHERE id = :id")
    fun getAlarm(id: Long) : Alarm

    @Query("SELECT * FROM alarm_table ORDER BY startTime ASC")
    fun getAllAlarm() : List<Alarm>

    @Query("DELETE FROM alarm_table WHERE id = :id")
    fun deleteAlarm(id: Long)

    @Transaction
    fun deleteAndGetAlarms(key: Long) : List<Alarm> {
        deleteAlarm(key)
        return getAllAlarm()
    }
}