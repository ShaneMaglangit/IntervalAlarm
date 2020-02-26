package com.shanemaglangit.intervalalarm.ui.alarm


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.shanemaglangit.intervalalarm.R
import com.shanemaglangit.intervalalarm.data.AlarmDatabase
import com.shanemaglangit.intervalalarm.data.AlarmDatabaseDao
import com.shanemaglangit.intervalalarm.databinding.FragmentAlarmBinding
import com.shanemaglangit.intervalalarm.util.AlarmAdapter
import com.shanemaglangit.intervalalarm.util.AlarmListener
import kotlinx.android.synthetic.main.fragment_alarm.*

class AlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmBinding
    private lateinit var alarmViewModel: AlarmViewModel
    private lateinit var databaseDao: AlarmDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val alarmAdapter = AlarmAdapter(AlarmListener { Log.i("AlarmFragment", it.toString()) })
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, container, false)
        databaseDao = AlarmDatabase.getInstance(context!!).alarmDao()
        alarmViewModel = ViewModelProvider(this, AlarmViewModelFactory(databaseDao)).get(AlarmViewModel::class.java)
        alarmViewModel.alarmList.observe(viewLifecycleOwner, Observer { alarmAdapter.submitList(it) })
        alarmViewModel.toAddFragment.observe(viewLifecycleOwner, Observer {
            if(it) {
                findNavController().navigate(R.id.action_alarmFragment_to_addFragment)
                alarmViewModel.navigateToFragmentComplete()
            }
        })
        binding.recyclerAlarms.layoutManager = LinearLayoutManager(context)
        binding.recyclerAlarms.adapter = alarmAdapter
        binding.alarmViewModel = alarmViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}
