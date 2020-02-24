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
