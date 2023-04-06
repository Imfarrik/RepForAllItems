package com.example.idk_quiz.ui.main

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.idk_quiz.Helper
import com.example.idk_quiz.R
import com.example.idk_quiz.databinding.MainFragmentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToQuizFragment(), Helper.navOptions()
            )
        }

        binding.datePicker.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.date_picker)).build()

            datePicker.addOnPositiveButtonClickListener {
                calendar.timeInMillis = it
                Snackbar.make(
                    binding.datePicker, dateFormat.format(calendar.time), Snackbar.LENGTH_SHORT
                ).show()
            }

            datePicker.show(parentFragmentManager, "datePicker")

        }

    }

}
