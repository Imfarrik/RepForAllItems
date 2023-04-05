package com.example.idk_quiz.ui.main

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.idk_quiz.Helper
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

        ObjectAnimator.ofFloat(textViewTitle, View.ROTATION, 0f, 360f).apply {
            duration = 3000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 1
            start()
        }

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

        val x = PropertyValuesHolder.ofFloat(View.ROTATION_Y, 0f, 360f)
        val colorTxt = PropertyValuesHolder.ofInt(
            "color",
            Color.parseColor("#7313E9"),
            Color.parseColor("#0EC400")
        ).apply {
            setEvaluator(ArgbEvaluator())
        }

        ObjectAnimator.ofPropertyValuesHolder(btnBack, x, colorTxt).apply {
            duration = 3000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 1
            start()
        }

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
                findNavController().navigate(
                    ResultFragmentDirections.actionResultFragmentToQuizFragment2(),
                    Helper.navOptions()
                )
            }
        }

        layout.addView(btnBack)
        bd.container.addView(layout)


    }

}