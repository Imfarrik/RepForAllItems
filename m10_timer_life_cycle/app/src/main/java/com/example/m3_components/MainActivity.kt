package com.example.m3_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.m3_components.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTime: Int = 0
    private var isCountdownRunning: Boolean = false
    private lateinit var job: Job

    companion object {
        const val MAX = 100
        const val ONE_SEC: Long = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState != null) {
            currentTime = savedInstanceState.getInt("currentTime")
            isCountdownRunning = savedInstanceState.getBoolean("isCountdownRunning")
        }

        if (isCountdownRunning) {

            binding.button.text = "Stop"

            binding.textView.text = currentTime.toString()

            binding.slider.isEnabled = false
            binding.button.isEnabled = false

            job = CoroutineScope(Dispatchers.Main).launch {
                binding.progressBar.max = binding.slider.value.toInt()
                while (true) {
                    currentTime--
                    delay(ONE_SEC)
                    Log.i("Hello", "$currentTime")
//                    currentPercentageOfProgressBar -= percentageOfProgressBar
                    binding.progressBar.progress = currentTime
                    binding.textView.text = currentTime.toString()
                    if (currentTime <= 0) {
                        Toast.makeText(
                            applicationContext,
                            "The countdown is over!",
                            Toast.LENGTH_SHORT
                        ).show()
                        isCountdownRunning = false
                        sliderAndBarUpdater()
                        binding.button.text = "Start"
                        break
                    }
                }
            }
            job.start()

        }

        binding.slider.addOnChangeListener { _, _, _ ->
            binding.progressBar.max = MAX
            if (!isCountdownRunning) {
                currentTime = binding.slider.value.toInt()
                binding.textView.text = currentTime.toString()
            }
        }

        binding.button.setOnClickListener {
            start()
        }

    }

    private fun sliderAndBarUpdater() {
        currentTime = binding.slider.value.toInt()
        binding.textView.text = currentTime.toString()
        binding.progressBar.progress = MAX
        binding.slider.isEnabled = true
        binding.button.isEnabled = true
    }

    private fun start() {


        binding.button.text = "Stop"
        Toast.makeText(
            applicationContext,
            "The countdown has begun!",
            Toast.LENGTH_SHORT
        ).show()

        sliderAndBarUpdater()

        binding.slider.isEnabled = false
        binding.button.isEnabled = false

        binding.progressBar.max = binding.slider.value.toInt()

        isCountdownRunning = true


        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                currentTime--
                delay(ONE_SEC)
                Log.i("Hello", "$currentTime")
                binding.progressBar.progress = currentTime
                binding.textView.text = currentTime.toString()
                if (currentTime <= 0) {
                    Toast.makeText(
                        applicationContext,
                        "The countdown is over!",
                        Toast.LENGTH_SHORT
                    ).show()
                    isCountdownRunning = false
                    sliderAndBarUpdater()
                    binding.button.text = "Start"
                    break
                }
            }
        }
        job.start()
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentTime", currentTime)
        outState.putBoolean("isCountdownRunning", isCountdownRunning)
    }

}


