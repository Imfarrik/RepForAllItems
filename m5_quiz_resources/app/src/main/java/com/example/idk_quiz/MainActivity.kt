package com.example.idk_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.idk_quiz.databinding.MainActivityBinding
import com.example.idk_quiz.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}