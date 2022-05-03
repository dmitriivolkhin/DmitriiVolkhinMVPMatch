package com.example.dmitriivolkhinmvpmatch

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.beust.klaxon.Klaxon
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class RequestToInternalStorage(private val filename: String = "") {
    fun writeToFile(context : Context) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            fileOutputStream?.write(Klaxon().toJsonObject(list).toString().toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOutputStream?.close()
        }
    }

    fun readFromFile(context : Context, callback: (List<FilmData>) -> Unit) {
        var fileInputStream: FileInputStream? = null
        val stringBuilder: StringBuilder = StringBuilder()
        try {
            fileInputStream = context.openFileInput(filename)
            val inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileInputStream?.close()
        }

        try {
            list.addAll(Klaxon().parse<List<FilmData>>(stringBuilder.toString())!!)
            callback(list)

        } catch (e: Exception) {
            Log.println(Log.ERROR, "MyApp", e.stackTrace.toString())
        }
    }

    private val list : MutableList<FilmData> = mutableListOf()

    fun add(data : FilmData) {
        list.add(data)
    }

    fun add(data : List<FilmData>) {
        list.addAll(data)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun remove(id : String) {
        list.removeIf { it -> it._id.compareTo(id) == 0 }
    }

    fun check(id: String) : Boolean {
        return list.any { it -> it._id.compareTo(id) == 0 }
    }
}
