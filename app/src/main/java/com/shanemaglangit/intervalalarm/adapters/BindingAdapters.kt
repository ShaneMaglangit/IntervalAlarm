package com.shanemaglangit.intervalalarm.util

import android.widget.SeekBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("time")
fun setTime(view: TextView, value: Long?) {
    val calendar = Calendar.getInstance()
    if(value != null) calendar.timeInMillis = value
    view.text = SimpleDateFormat("hh : mm a").format(calendar.time).toUpperCase()
}

@BindingAdapter("startTime", "endTime")
fun setListTime(view: TextView, startTime: Long, endTime: Long) {
    val calendar = Calendar.getInstance()
    val start: String
    val end: String
    calendar.timeInMillis = startTime
    start = SimpleDateFormat("hh:mma").format(calendar.time)
    calendar.timeInMillis = endTime
    end = SimpleDateFormat("hh:mma").format(calendar.time)
    view.text = "$start to $end"
}

@BindingAdapter("android:text")
fun setText(view: TextView, value: Long) {
    view.text = value.toString()
}

@BindingAdapter("days")
fun setDays(view: TextView, value: List<String>) {
    if(value.isNotEmpty()) {
        var daysInString = ""
        value.forEach {
            daysInString += "$it "
        }
        view.text = daysInString
    } else {
        view.text = "Only once"
    }
}

@BindingAdapter("android:progress")
fun setProgress(view: SeekBar, value: Long?) {
    val intervalInMinutes = if(value == null) 0 else value.toInt() / 1000 / 60 / 5 - 1
    if(view.progress != intervalInMinutes) view.progress = intervalInMinutes
}

@InverseBindingAdapter(attribute="android:progress")
fun getProgress(view: SeekBar) : Long = (view.progress.toLong() + 1) * 5 * 60 * 1000

@BindingAdapter("interval")
fun setInterval(view: TextView, value: Long) {
    view.text  = "${value / 60000} minutes interval"
}