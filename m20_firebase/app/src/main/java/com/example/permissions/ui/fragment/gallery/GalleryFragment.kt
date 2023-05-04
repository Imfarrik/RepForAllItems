package com.example.permissions.ui.fragment.gallery

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.permissions.App
import com.example.permissions.R
import com.example.permissions.databinding.FragmentGalleryBinding
import com.example.permissions.ui.helper.Helper
import com.example.permissions.ui.helper.Navigator
import com.example.permissions.model.data.State
import com.example.permissions.ui.adapters.PhotoAdapter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import java.util.Date

class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(binding.root)

        initView()

        viewModel.getDB()

    }

    private fun initView() = with(binding) {

        mapKit.setOnClickListener {

//            Navigator.actionGalleryFragmentToMapFragment(findNavController())

            FirebaseCrashlytics.getInstance().log("On navigation on new fragment")

            createNotification()

            try {
                throw RuntimeException("Test Crash")
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }

            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                Log.i("token", Date().time.toString())
            }


        }

        imageButton.setOnClickListener {
            Navigator.actionGalleryFragmentToCameraFragment(findNavController())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect {
                    when (it) {
                        State.Loading -> {
                            photoRv.isVisible = false
                            tvNoPhoto.isVisible = false
                            progressBar.isVisible = true
                        }

                        is State.Success -> {
                            progressBar.isVisible = false
                            tvNoPhoto.isVisible = false
                            photoRv.isVisible = true

                            val linLayoutManager = GridLayoutManager(requireContext(), 3)

                            photoRv.apply {
                                layoutManager = linLayoutManager
                                setHasFixedSize(true)


                                adapter = PhotoAdapter(it.onSuccess!!, true) { photo, _ ->
                                    viewModel.deletePhoto(photo)
                                }

                                if (it.onSuccess.isNotEmpty()) {
                                    smoothScrollToPosition(it.onSuccess.lastIndex)
                                }

                                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                                    override fun onScrolled(
                                        recyclerView: RecyclerView,
                                        dx: Int,
                                        dy: Int
                                    ) {
                                        if (dy > 0 || dy < 0 && binding.imageButton.isVisible) {
                                            binding.imageButton.isVisible = false
                                        }
                                    }

                                    override fun onScrollStateChanged(
                                        recyclerView: RecyclerView,
                                        newState: Int
                                    ) {
                                        val handler = Handler(Looper.getMainLooper())
                                        handler.postDelayed({
                                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                                binding.imageButton.isVisible = true
                                            }
                                        }, 500)
                                        super.onScrollStateChanged(recyclerView, newState)
                                    }
                                })

                            }


                        }

                        State.Error -> {
                            progressBar.isVisible = false
                            photoRv.isVisible = false
                            tvNoPhoto.isVisible = true
                        }
                    }
                }


            }
        }


    }

    private fun createNotification() {

        val notification = NotificationCompat.Builder(requireContext(), App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.record_btn)
            .setContentTitle("Test notification")
            .setContentText("Description on test notification")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permission(requireActivity())
        } else {
            NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
        }

    }

    companion object {
        const val NOTIFICATION_ID = 1
    }

    private fun permission(requireActivity: Activity) {
        if (ContextCompat.checkSelfPermission(
                requireActivity, "android.permission.POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity, arrayOf("android.permission.POST_NOTIFICATIONS"), 1
            )
        }
    }


}