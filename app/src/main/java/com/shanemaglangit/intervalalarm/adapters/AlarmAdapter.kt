package com.shanemaglangit.intervalalarm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shanemaglangit.intervalalarm.data.Alarm
import com.shanemaglangit.intervalalarm.databinding.AlarmItemBinding

class AlarmAdapter(val alarmListener: AlarmListener) : ListAdapter<Alarm, AlarmAdapter.ViewHolder>(
    AlarmDiffCallback()
) {
    public override fun getItem(position: Int): Alarm {
        return super.getItem(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, alarmListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: AlarmItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Alarm, alarmListener: AlarmListener) {
            binding.alarm = item
            binding.alarmListener = alarmListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AlarmItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}

class AlarmListener(val clickListener: (alarmId: Long) -> Unit) {
    fun onClick(alarm: Alarm) = clickListener(alarm.id)
}

class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {

    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }
}