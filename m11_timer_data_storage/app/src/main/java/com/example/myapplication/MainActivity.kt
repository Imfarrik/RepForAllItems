package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        repository = Repository(this)

        initView()

        initToast()

    }

    private fun initView() = with(vb) {

        save.setOnClickListener {
            repository.saveText(editText.text.toString())
            txt.text = repository.getText()
        }

        clear.setOnClickListener {
            repository.clearText()
            txt.text = ""
            editText.setText("")
        }

    }

    private fun initToast() {
        repository.isSavedLD.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}