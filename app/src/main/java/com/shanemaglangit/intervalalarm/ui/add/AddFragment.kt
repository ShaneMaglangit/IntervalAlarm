package com.shanemaglangit.intervalalarm.ui.add


import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.shanemaglangit.intervalalarm.R
import com.shanemaglangit.intervalalarm.data.AlarmDatabase
import com.shanemaglangit.intervalalarm.data.AlarmDatabaseDao
import com.shanemaglangit.intervalalarm.databinding.FragmentAddBinding
import com.shanemaglangit.intervalalarm.service.AlarmService
import java.util.*

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var addViewModel: AddViewModel
    private lateinit var databaseDao: AlarmDatabaseDao
    private lateinit var alarmManager: AlarmManager

    companion object {
        const val START_TIME = 0
        const val END_TIME = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = requireContext()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        databaseDao = AlarmDatabase.getInstance(context).alarmDao()
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        addViewModel = ViewModelProvider(this, AddViewModelFactory(databaseDao)).get(AddViewModel::class.java)
        addViewModel.startTimePicker.setTimePicker(START_TIME)
        addViewModel.endTimePicker.setTimePicker(END_TIME)
        addViewModel.newAlarmId.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                startService()
                findNavController().navigate(R.id.action_addFragment_to_alarmFragment)
            }
        })
        binding.addViewModel = addViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun startService() {
        val alarmIntent = Intent(context, AlarmService::class.java)
        if(Build.VERSION.SDK_INT >= 26) context!!.startForegroundService(alarmIntent)
        else context!!.startService(alarmIntent)
    }

    private fun LiveData<Boolean>.setTimePicker(type : Int) {
        this.observe(viewLifecycleOwner, Observer {
            if(it) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis =
                    when(type) {
                        START_TIME -> addViewModel.startTime.value!!
                        END_TIME -> addViewModel.endTime.value!!
                        else -> calendar.timeInMillis
                    }
                TimePickerDialog(
                    context,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        addViewModel.setTime(calendar.timeInMillis, type)
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
                ).show()
            }
        })
    }
}
