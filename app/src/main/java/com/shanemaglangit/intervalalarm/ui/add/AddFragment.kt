package com.shanemaglangit.intervalalarm.ui.add


import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

import com.shanemaglangit.intervalalarm.R
import com.shanemaglangit.intervalalarm.databinding.FragmentAddBinding
import java.time.LocalTime
import java.util.*

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var addViewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        addViewModel.startTimePicker.observe(this, Observer {
            if(it) {
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    context,
                    R.style.Theme_MaterialComponents_DayNight_Dialog,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
                ).show()
            }
        })

        binding.addViewModel = addViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}
