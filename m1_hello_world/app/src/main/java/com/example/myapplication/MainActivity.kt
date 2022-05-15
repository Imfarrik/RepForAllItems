package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var textOne = 50
        var textTwo = 0

        binding.text1.setTextColor(Color.GREEN)
        binding.text1.text = "Все места свободны"

        binding.buttonPlus.setOnClickListener {
            when (textOne) {
                0 -> {
                    binding.text1.setTextColor(Color.RED)
                    binding.text1.text = "Пассажиров слишком много!"
                    binding.buttonPlus.isEnabled = false
                    binding.buttonClean.isVisible = true
                }
                else -> {
                    textOne --
                    textTwo ++
                    binding.text1.setTextColor(Color.BLUE)
                    binding.text1.text = "Осталось мест: $textOne"
                    binding.text2.text = textTwo.toString()
                }
            }
        }

        binding.buttonMinus.setOnClickListener {
            when (textOne) {
                50 -> {
                    binding.buttonMinus.isEnabled = false
                    binding.buttonClean.isVisible = true
                }
                else -> {
                    textOne++
                    textTwo--
                    binding.text1.setTextColor(Color.BLUE)
                    binding.text1.text = "Осталось мест: $textOne"
                    binding.text2.text = textTwo.toString()
                }
            }
        }

        binding.buttonClean.setOnClickListener {
            binding.buttonClean.isVisible = false
            binding.buttonMinus.isEnabled = true
            binding.buttonPlus.isEnabled = true
            textOne = 50
            textTwo = 0
            binding.text1.setTextColor(Color.GREEN)
            binding.text1.text = "Все места свободны"
            binding.text2.text = textTwo.toString()
        }
    }
}
