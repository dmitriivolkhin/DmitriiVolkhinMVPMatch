package com.example.dmitriivolkhinmvpmatch

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

class FilmDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        RequestToOMDB.getById(this, ::callback, intent.getStringExtra("id"))

        if (intent.getStringExtra("id")?.let { ListFavoriteFilms.favorite.check(it) }!!) {
            findViewById<ImageButton>(R.id.film_detail_fav_btn).setImageResource(android.R.drawable.btn_star_big_on)
        }

        findViewById<ImageButton>(R.id.film_detail_fav_btn).setOnClickListener(object: View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(view: View?) {
                if (intent.getStringExtra("id")?.let { ListFavoriteFilms.favorite.check(it) }!!) {
                    ListFavoriteFilms.favorite.remove(intent.getStringExtra("id")!!)
                    findViewById<ImageButton>(R.id.film_detail_fav_btn).setImageResource(android.R.drawable.btn_star_big_off)
                } else {
                    RequestToOMDB.getById(this@FilmDetails, { data: FilmData -> ListFavoriteFilms.favorite.add(data) }, intent.getStringExtra("id"))
                    findViewById<ImageButton>(R.id.film_detail_fav_btn).setImageResource(android.R.drawable.btn_star_big_on)
                }
            }
        })

        findViewById<ImageButton>(R.id.film_detail_hid_btn).setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                if (intent.getStringExtra("id")?.let { ListHiddenFilms.hidden.check(it) }!!) {
                    RequestToOMDB.getById(this@FilmDetails, { data: FilmData -> ListHiddenFilms.hidden.add(data) }, intent.getStringExtra("id"))
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun callback(data: FilmData) {
        findViewById<TextView>(R.id.film_detail_page_title).text = data._title
        findViewById<TextView>(R.id.film_detail_title).text = data._title
        findViewById<TextView>(R.id.film_detail_rating).text = data._rating
        findViewById<TextView>(R.id.film_detail_release_year).text = "Release: " + data._released
        findViewById<ImageView>(R.id.film_detail_poster).setImageURI(Uri.parse(data._poster))

        findViewById<TextView>(R.id.film_detail_description).text = data._description
        findViewById<TextView>(R.id.film_detail_director).text = data._director?.replace(", ", "\n")
        findViewById<TextView>(R.id.film_detail_actors).text = data._actors?.replace(", ", "\n")
    }
}