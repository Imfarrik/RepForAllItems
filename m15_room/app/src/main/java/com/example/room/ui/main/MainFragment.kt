package com.example.room.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.room.databinding.FragmentMainBinding
import com.example.room.model.Word
import com.example.room.room.WordDao
import com.example.room.room.WordDatabase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            val word = binding.wordInput.text.toString().trim().lowercase()
            viewModel.addWord(word)
            binding.wordInput.text?.clear()
        }

        binding.clearButton.setOnClickListener {
            viewModel.clearDB()
        }

        lifecycleScope.launch {
            viewModel.dao.getTopWords().collect { topWords ->
                val text = topWords.joinToString(separator = ", ") { "${it.word} (${it.count})" }
                binding.topWordsTextView.text = "Top words: $text"
            }
        }

    }

}