package com.example.nasa.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.nasa.R
import com.example.nasa.databinding.FragmentFullPhotoBinding
import com.example.nasa.domain.Helper


class FullPhotoFragment : Fragment() {

    private lateinit var binding: FragmentFullPhotoBinding
    private val args: FullPhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(binding.root)

        initView()
    }

    private fun initView() = with(binding) {

        Helper.setToast(requireContext(), "You can zoom image :)")

        Glide.with(requireContext())
            .load(args.url)
            .into(photoView)

        backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }


}