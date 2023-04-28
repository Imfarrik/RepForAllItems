package com.example.nasa.domain

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.nasa.R
import com.example.nasa.ui.ChooseFragmentDirections
import com.example.nasa.ui.MainFragmentDirections
import com.example.nasa.ui.PagingFragmentDirections

object Navigation {

    private fun navOptions(): NavOptions {
        return NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_from_right)
            .setExitAnim(R.anim.slide_to_left)
            .setPopEnterAnim(R.anim.slide_from_left)
            .setPopExitAnim(R.anim.slide_to_right)
            .build()
    }

    fun actionMainFragmentToFullPhotoFragment(navController: NavController, arg: String) {
        if (navController.currentDestination?.id == R.id.mainFragment2) {

            navController.navigate(
                MainFragmentDirections.actionMainFragment2ToFullPhotoFragment2(arg),
                navOptions()
            )

        }
    }

    fun actionPagingFragment2ToFullPhotoFragment2(navController: NavController, arg: String) {
        if (navController.currentDestination?.id == R.id.pagingFragment2) {

            navController.navigate(
                PagingFragmentDirections.actionPagingFragment2ToFullPhotoFragment2(arg),
                navOptions()
            )

        }
    }

    fun actionChooseFragment2ToMainFragment2(navController: NavController) {
        if (navController.currentDestination?.id == R.id.chooseFragment2) {

            navController.navigate(
                ChooseFragmentDirections.actionChooseFragment2ToMainFragment2(),
                navOptions()
            )

        }
    }

    fun actionChooseFragment2ToPagingFragment2(navController: NavController) {
        if (navController.currentDestination?.id == R.id.chooseFragment2) {

            navController.navigate(
                ChooseFragmentDirections.actionChooseFragment2ToPagingFragment2(),
                navOptions()
            )

        }
    }


}