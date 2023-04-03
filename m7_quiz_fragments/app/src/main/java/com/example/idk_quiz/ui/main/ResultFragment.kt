package com.example.idk_quiz.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.idk_quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var bd: FragmentResultBinding
    private val arg by navArgs<ResultFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bd = FragmentResultBinding.inflate(inflater)
        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arg.result

        val textViewTitle = TextView(requireContext())

        textViewTitle.apply {
            text = result
            setTextColor(Color.RED)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 10, 0, 10)
            layoutParams = params
        }
        bd.container.addView(textViewTitle)

        val btnBack = Button(requireContext())
        val layout = LinearLayout(requireContext())

        layout.apply {

            orientation = LinearLayout.HORIZONTAL

            gravity = Gravity.CENTER

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 50, 0, 50)
            layoutParams = params
        }

        btnBack.apply {
            text = "Начать заново"
            setOnClickListener {
                findNavController().navigate(ResultFragmentDirections.actionResultFragmentToQuizFragment2())
            }
        }

        layout.addView(btnBack)
        bd.container.addView(layout)


    }

}