package com.example.permissions.ui.helper

import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.permissions.R
import com.example.permissions.ui.activity.MainActivity
import com.example.permissions.ui.activity.WelcomeActivity
import com.example.permissions.ui.fragment.gallery.GalleryFragmentDirections

object Navigator {

    private fun navOptions(): NavOptions {
        return NavOptions.Builder().setLaunchSingleTop(true).setEnterAnim(R.anim.slide_from_right)
            .setExitAnim(R.anim.slide_to_left).setPopEnterAnim(R.anim.slide_from_left)
            .setPopExitAnim(R.anim.slide_to_right).build()
    }

    fun actionGalleryFragmentToCameraFragment(navController: NavController) {
        if (navController.currentDestination?.id == R.id.galleryFragment) {

            navController.navigate(
                GalleryFragmentDirections.actionGalleryFragmentToCameraFragment(), navOptions()
            )

        }
    }

    fun actionGalleryFragmentToMapFragment(navController: NavController) {
        if (navController.currentDestination?.id == R.id.galleryFragment) {

            navController.navigate(
                GalleryFragmentDirections.actionGalleryFragmentToMapFragment(), navOptions()
            )

        }
    }

    fun startAuthActivity(context: WelcomeActivity) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        ActivityCompat.finishAffinity(context)
    }


}