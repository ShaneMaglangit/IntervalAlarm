package com.shanemaglangit.intervalalarm.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface AlarmDatabaseDao {
    @Insert
    fun insert(alarm: Alarm) : Long
}