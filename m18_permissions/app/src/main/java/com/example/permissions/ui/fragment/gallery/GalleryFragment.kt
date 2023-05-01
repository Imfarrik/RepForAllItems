package com.example.permissions.ui.fragment.gallery

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.permissions.databinding.FragmentGalleryBinding
import com.example.permissions.ui.helper.Helper
import com.example.permissions.ui.helper.Navigator
import com.example.permissions.model.data.State
import com.example.permissions.ui.adapters.PhotoAdapter
import kotlinx.coroutines.launch

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


}