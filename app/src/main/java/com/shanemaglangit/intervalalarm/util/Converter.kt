package com.shanemaglangit.intervalalarm.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    inline fun <reified T> Gson.fromJson(json: String) =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    @TypeConverter
    fun fromList(strings: List<String>): String = Gson().toJson(strings)

    @TypeConverter
    fun fromString(value: String): List<String> = Gson().fromJson(value)
}