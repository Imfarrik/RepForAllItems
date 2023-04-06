package com.example.idk_quiz

import androidx.navigation.NavOptions

interface Helper {

    companion object {
        fun navOptions(): NavOptions {
            return NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.slide_from_right)
                .setExitAnim(R.anim.slide_to_left)
                .setPopEnterAnim(R.anim.slide_from_left)
                .setPopExitAnim(R.anim.slide_to_right)
                .build()
        }
    }

}