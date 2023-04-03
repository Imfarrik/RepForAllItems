package com.example.idk_quiz.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.idk_quiz.databinding.FragmentQuizBinding
import com.example.idk_quiz.quiz.QuizStorage


class QuizFragment : Fragment() {


    private lateinit var bd: FragmentQuizBinding
    private val quiz = QuizStorage.getQuiz(QuizStorage.Locale.Ru)
    private val list = mutableListOf<Int>()
    private var one: Int? = null
    var two: Int? = null
    var three: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bd = FragmentQuizBinding.inflate(inflater)
        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addViewTest()

    }

    private fun addViewTest() = with(bd) {

        quiz.questions.forEach {
            val textView = TextView(requireContext())
            val radioGroup = RadioGroup(requireContext())
            addTextView(textView, it.question)
            addRadioBtn(radioGroup, it.answers, it.question, it.feedback)

            container.addView(textView)
            container.addView(radioGroup)
        }

        addExtraBtn(container)

    }

    private fun addTextView(textView: TextView, textQ: String) {
        textView.apply {

            text = textQ
            setTextColor(Color.BLACK)

            val params = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 50, 0, 50)
            layoutParams = params

        }
    }

    private fun addRadioBtn(
        radioGroup: RadioGroup,
        answer: List<String>,
        question: String,
        feedBack: List<String>
    ) {

        for (i in answer.indices) {
            val rButton = RadioButton(requireContext())
            rButton.apply {
                id = View.generateViewId()
                text = answer[i]
                setOnClickListener {
                    when (question) {
                        quiz.questions[0].question -> one = i
                        quiz.questions[1].question -> two = i
                        quiz.questions[2].question -> three = i
                    }
                }
            }

            radioGroup.addView(rButton)
        }

    }

    private fun addExtraBtn(container: LinearLayout) {
        val btnBack = Button(requireContext())
        val btnNext = Button(requireContext())
        val layout = LinearLayout(requireContext())
        val layout1 = LinearLayout(requireContext())

        layout.apply {

            orientation = LinearLayout.HORIZONTAL

            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 50, 0, 50)
            layoutParams = params
        }

        btnBack.apply {
            text = "Назад"
            setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        layout1.apply {

            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END

            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            layoutParams = params
        }

        btnNext.apply {
            text = "Отправить"
            setOnClickListener {
                if (one != null && two != null && three != null) {
                    list.clear()
                    list.add(one!!)
                    list.add(two!!)
                    list.add(three!!)
                    findNavController().navigate(
                        QuizFragmentDirections.actionQuizFragmentToResultFragment(
                            QuizStorage.answer(quiz, list)
                        )
                    )
                } else {
                    Toast.makeText(requireContext(), "Ответьте на все вопросы", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        container.addView(layout)
        layout.addView(btnBack)
        layout1.addView(btnNext)
        layout.addView(layout1)

    }

}