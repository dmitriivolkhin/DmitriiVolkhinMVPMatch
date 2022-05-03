package com.example.dmitriivolkhinmvpmatch

import android.content.ComponentCallbacks
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Json
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser

object RequestToOMDB {
    private val keyAPI = "91fa7b9"

    fun search (context : Context, callback: (List<FilmData>) -> Unit, name : String?) {
        val url = "https://www.omdbapi.com/?s=" + name + "&apikey=" + keyAPI

        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    class P (@Json(name = "Search") val p0: List<FilmData>) {}
                    callback(Klaxon().parse<P>(response)!!.p0)

                } catch (e: Exception) {
                    Log.println(Log.ERROR, "MyApp", e.stackTrace.toString())
                }
            },
            { })

        queue.add(stringRequest)
    }

    fun getById(context : Context, callback: (FilmData) -> Unit, id : String?) {
        val url = "https://www.omdbapi.com/?i=" + id + "&apikey=" + keyAPI

        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    callback(Klaxon().parse<FilmData>(response)!!)

                } catch (e: Exception) {
                    Log.println(Log.ERROR, "MyApp", e.stackTrace.toString())
                }
            },
            { })

        queue.add(stringRequest)
    }
}