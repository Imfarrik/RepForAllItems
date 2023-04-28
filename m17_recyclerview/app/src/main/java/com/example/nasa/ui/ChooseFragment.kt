package com.example.nasa.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nasa.R
import com.example.nasa.databinding.FragmentChooseBinding
import com.example.nasa.domain.Helper
import com.example.nasa.domain.Navigation


class ChooseFragment : Fragment() {

    private lateinit var binding: FragmentChooseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.insets(binding.root)

        initView()
    }

    private fun initView() = with(binding) {

        btnNoPagination.setOnClickListener {
            Navigation.actionChooseFragment2ToMainFragment2(findNavController())
        }

        btnPagination.setOnClickListener {
            Navigation.actionChooseFragment2ToPagingFragment2(findNavController())
        }


    }


}