package com.example.m3_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.m3_components.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTime: Int = 10
    private var currentPercentageOfProgressBar: Int = MAX

    companion object {
        const val MAX = 100
        const val ONE_SEC: Long = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sliderAndBarUpdater = {
            currentPercentageOfProgressBar = MAX
            currentTime = binding.slider.value.toInt()
            binding.textView.text = currentTime.toString()
            binding.progressBar.progress = currentPercentageOfProgressBar
            binding.slider.isEnabled = true
            binding.button.isEnabled = true
        }

        binding.slider.addOnChangeListener { _, _, _ ->
            sliderAndBarUpdater()
        }

        binding.button.setOnClickListener {
            binding.button.text = "Stop"
            Toast.makeText(applicationContext,
                "The countdown has begun!",
                Toast.LENGTH_SHORT).show()
            sliderAndBarUpdater()
            binding.slider.isEnabled = false
            binding.button.isEnabled = false
            val percentageOfProgressBar = MAX / currentTime
            CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    currentTime--
                    delay(ONE_SEC)
                    currentPercentageOfProgressBar -= percentageOfProgressBar
                    binding.progressBar.progress = currentPercentageOfProgressBar
                    binding.textView.text = currentTime.toString()
                    if (currentTime <= 0) {
                        Toast.makeText(applicationContext,
                            "The countdown is over!",
                            Toast.LENGTH_SHORT).show()
                        sliderAndBarUpdater()
                        binding.button.text = "Start"
                        break
                    }
                }
            }
        }

    }
}