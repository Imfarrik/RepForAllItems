package com.example.room.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.room.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words_table WHERE word = :word")
    suspend fun getWordCount(word: String): Word?

    @Query("SELECT * FROM words_table ORDER BY count DESC LIMIT 5")
    fun getTopWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordCount(wordCount: Word)

    @Query("DELETE FROM words_table")
    suspend fun clearAll()
}