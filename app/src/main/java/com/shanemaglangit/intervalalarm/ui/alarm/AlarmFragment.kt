package com.shanemaglangit.intervalalarm.ui.alarm


import android.content.Intent
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.shanemaglangit.intervalalarm.R
import com.shanemaglangit.intervalalarm.data.AlarmDatabase
import com.shanemaglangit.intervalalarm.data.AlarmDatabaseDao
import com.shanemaglangit.intervalalarm.databinding.FragmentAlarmBinding
import com.shanemaglangit.intervalalarm.adapters.AlarmAdapter
import com.shanemaglangit.intervalalarm.adapters.AlarmListener
import com.shanemaglangit.intervalalarm.service.AlarmService
import com.shanemaglangit.intervalalarm.util.SwipeToDeleteCallback
import timber.log.Timber

class AlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmBinding
    private lateinit var alarmViewModel: AlarmViewModel
    private lateinit var databaseDao: AlarmDatabaseDao
    private lateinit var swipeHandler: SwipeToDeleteCallback
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val alarmAdapter = AlarmAdapter( AlarmListener { Log.i("AlarmFragment", it.toString()) })
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, container, false)
        databaseDao = AlarmDatabase.getInstance(context!!).alarmDao()
        alarmViewModel = ViewModelProvider(this, AlarmViewModelFactory(databaseDao)).get(AlarmViewModel::class.java)
        alarmViewModel.alarmList.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()) context!!.stopService(Intent(context, AlarmService::class.java))
            alarmAdapter.submitList(it)
        })
        alarmViewModel.toAddFragment.observe(viewLifecycleOwner, Observer {
            if(it) {
                findNavController().navigate(R.id.action_alarmFragment_to_addFragment)
                alarmViewModel.navigateToFragmentComplete()
            }
        })
        swipeHandler = object: SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val alarm = alarmAdapter.getItem(viewHolder.adapterPosition)
                alarmViewModel.removeAlarm(alarm.id)
            }
        }
        itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerAlarms)
        binding.recyclerAlarms.layoutManager = LinearLayoutManager(context)
        binding.recyclerAlarms.adapter = alarmAdapter
        binding.alarmViewModel = alarmViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}
