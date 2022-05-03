package com.example.dmitriivolkhinmvpmatch

import com.beust.klaxon.Json

class FilmData (@Json(name = "imdbID")      val _id:            String,
                @Json(name = "Title")       val _title:         String? = "",
                @Json(name = "Year")        val _year:          String? = "",
                @Json(name = "Poster")      val _poster:        String? = "",
                @Json(name = "Released")    val _released:      String? = "",
                @Json(name = "Runtime")     val _runtime:       String? = "",
                @Json(name = "Director")    val _director:      String? = "",
                @Json(name = "Actors")      val _actors:        String? = "",
                @Json(name = "Plot")        val _description:   String? = "",
                @Json(name = "imdbRating")  val _rating:        String? = "") {
}