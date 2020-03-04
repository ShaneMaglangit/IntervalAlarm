package com.shanemaglangit.intervalalarm.ui.active

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shanemaglangit.intervalalarm.R
import com.shanemaglangit.intervalalarm.databinding.ActivityActiveAlarmBinding
import com.shanemaglangit.intervalalarm.ui.add.AddViewModel
import com.shanemaglangit.intervalalarm.ui.add.AddViewModelFactory
import com.shanemaglangit.intervalalarm.ui.alarm.AlarmViewModel
import com.shanemaglangit.intervalalarm.ui.alarm.AlarmViewModelFactory
import kotlinx.coroutines.NonCancellable.start
import timber.log.Timber

class ActiveAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActiveAlarmBinding
    private lateinit var activeAlarmViewModel: ActiveAlarmViewModel
    private lateinit var slidingAlarmAnimation: ObjectAnimator
    private lateinit var resetSlidingAlarm: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActiveAlarmBinding.inflate(layoutInflater)
        activeAlarmViewModel = ViewModelProvider(this, ActiveAlarmViewModelFactory(application)).get(
            ActiveAlarmViewModel::class.java)


        activeAlarmViewModel.isDone.observe(this, Observer {
            if(it) this.finishAndRemoveTask()
        })

        binding.buttonDismiss.setOnClickListener { activeAlarmViewModel.dismissAlarm() }

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.imageView.post {
            resetSlidingAlarm = ObjectAnimator.ofFloat(
                binding.imageView,
                "translationX",
                binding.imageView.x
            ).apply {
                duration = 0
                doOnEnd { slidingAlarmAnimation.start() }
            }

            slidingAlarmAnimation = ObjectAnimator.ofFloat(
                binding.imageView,
                "translationX",
                binding.root.width.toFloat() + binding.imageView.width.toFloat()
            ).apply {
                duration = 4000
                doOnEnd { resetSlidingAlarm.start() }
            }

            slidingAlarmAnimation.start()
        }
    }
}
