package com.example.room.ui.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.room.model.Word
import com.example.room.room.WordDao
import com.example.room.room.WordDatabase
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private var db: WordDatabase = WordDatabase.getDatabase(application)
    var dao: WordDao = db.wordDao()

    fun addWord(inputWord: String) {
        if (inputWord.isNotEmpty() && inputWord.matches(Regex("[a-z\\-]+"))) {
            viewModelScope.launch {
                val wordCount = dao.getWordCount(inputWord)
                if (wordCount == null) {
                    dao.insertWordCount(Word(inputWord, 1))
                } else {
                    wordCount.count += 1
                    dao.insertWordCount(wordCount)
                }
            }
        } else {
            Toast.makeText(application, "Invalid word", Toast.LENGTH_SHORT).show()
        }
    }

    fun clearDB() {
        viewModelScope.launch {
            dao.clearAll()
        }
    }

}