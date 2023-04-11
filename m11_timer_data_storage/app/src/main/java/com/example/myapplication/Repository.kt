package com.example.myapplication

import android.content.Context
import androidx.lifecycle.MutableLiveData
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class Repository(private val context: Context) {

    private val isSaved = MutableLiveData<String?>()
    val isSavedLD = isSaved

    companion object {
        const val SHARED_P = "SHARED_P"
        const val GET_TEXT = "get_text"
        const val FILE_NAME = "testFile.txt"
    }

    private var preference = context.getSharedPreferences(SHARED_P, Context.MODE_PRIVATE)

    fun saveText(text: String) {

        val editor = preference.edit()
        editor.putString(GET_TEXT, text)
        editor.apply()

        var fos: FileOutputStream? = null

        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fos.write(text.toByteArray())

            isSaved.value = "Файл сохранен"
        } catch (e: IOException) {
            isSaved.value = e.message
        } finally {
            fos?.close()
        }


    }

    private fun getDataFromSharedPreference(): String? {
        return preference.getString(GET_TEXT, null)
    }

    private fun getDataFromLocalVariable(): String? {
        var fin: FileInputStream? = null

        return try {
            fin = context.openFileInput(FILE_NAME)
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            String(bytes)
        } catch (e: IOException) {
            isSaved.value = e.message + "2"
            null
        } finally {
            fin?.close()
        }

    }

    fun clearText() {
        val preferences = preference.edit()
        preferences.clear()
        preferences.apply()

        context.deleteFile(FILE_NAME)

        isSaved.value = "Очищено"
    }

    fun getText(): String {
        return if (getDataFromLocalVariable() != null) {
            getDataFromLocalVariable()!!
        } else {
            getDataFromSharedPreference()!!
        }
    }


}