package com.example.mvvmtest.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvmtest.databinding.FragmentMainBinding
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val vm: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        getState()

    }

    private fun initView() = with(binding) {

        message.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                button.isEnabled = !(s != null && s.length < 3)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        button.setOnClickListener {
            val text = message.text.toString()
            if (text.length >= 3) {
                vm.onSearchClick(text)
            }
        }

    }

    private fun getText() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.textF.collect {
                if (it != "") {
                    tvTxt.text = "По запросу \"$it\" ничего не найдено"
                }
            }
        }
    }

    private fun getState() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launch {
            vm.stateF.collect {
                when (it) {
                    State.Loading -> {
                        progress.isVisible = true
                        button.isEnabled = false
                    }
                    State.Success -> {
                        progress.isVisible = false
                        button.isEnabled = binding.message.text.length > 3
                        getText()
                    }
                }
            }
        }

    }

}