package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.myapplication.databinding.MyCustomStyleBinding

class MyCustomStyle
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val binding = MyCustomStyleBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setText(text: String, gravity: String) {
        when {
            gravity.contains("up") -> binding.textUp.text = text
            gravity.contains("down") -> binding.textDown.text = text
        }
    }

}