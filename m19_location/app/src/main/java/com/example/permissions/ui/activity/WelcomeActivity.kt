package com.example.permissions.ui.activity

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.permissions.R
import com.example.permissions.databinding.ActivityWelcomeBinding
import com.example.permissions.ui.helper.Navigator

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var animation: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)


        animation = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        animation.setAnimationListener(animationFadeOutListener)

        val thread: Thread = object : Thread() {
            override fun run() {
                super.run()
                try {
                    sleep(2000)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {

                    Navigator.startAuthActivity(this@WelcomeActivity)

                }
            }
        }

        thread.start()

    }

    private var animationFadeOutListener: Animation.AnimationListener =
        object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        }
}