package com.shanemaglangit.intervalalarm.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
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
    start = SimpleDateFormat("hh:mm a").format(calendar.time).toUpperCase()
    calendar.timeInMillis = endTime
    end = SimpleDateFormat("hh:mm a").format(calendar.time).toUpperCase()
    view.text = "$start to $end"
}

@BindingAdapter("android:text")
fun setText(view: TextView, value: Int) {
    view.text = value.toString()
}

//@BindingAdapter("listInterval")
//fun setListInterval(view: TextView, value: Int) {
//    view.text = value.toString()
//}