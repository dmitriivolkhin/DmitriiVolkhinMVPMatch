package com.example.dmitriivolkhinmvpmatch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity

class FilmListViewAdapter(val context: Context, val list: List<FilmData>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item, p2, false)

        rowView.tag = list[p0]._id
        rowView.findViewById<TextView>(R.id.list_item_title).text = list[p0]._title
        rowView.findViewById<TextView>(R.id.list_item_release_year).text = "Release year: " + list[p0]._year
        rowView.findViewById<ImageView>(R.id.list_item_poster).setImageURI(Uri.parse(list[p0]._poster))

        rowView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                val intent = Intent(context, FilmDetails::class.java)
                var options = Bundle().apply {
                    putString("id", view?.tag.toString())
                }
                intent.putExtra("id", view?.tag.toString())
                startActivity(context, intent, options)
            }
        })

        if (ListFavoriteFilms.favorite.check(list[p0]._id)) {
            rowView.findViewById<ImageButton>(R.id.list_item_fav_btn).setImageResource(android.R.drawable.btn_star_big_on)
        }

        rowView.findViewById<ImageButton>(R.id.list_item_fav_btn).setOnClickListener(object: View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(view: View?) {
                if (ListFavoriteFilms.favorite.check(view?.tag.toString())) {
                    ListFavoriteFilms.favorite.remove(view?.tag.toString())
                    rowView.findViewById<ImageButton>(R.id.list_item_fav_btn).setImageResource(android.R.drawable.btn_star_big_off)
                } else {
                    ListFavoriteFilms.favorite.add(list.filter { it -> it._id.compareTo(view?.tag.toString()) == 0 })
                    rowView.findViewById<ImageButton>(R.id.list_item_fav_btn).setImageResource(android.R.drawable.btn_star_big_on)
                }
            }
        })

        rowView.findViewById<ImageButton>(R.id.list_item_hid_btn).setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                if (ListHiddenFilms.hidden.check(view?.tag.toString())) {
                    RequestToOMDB.getById(context, { data: FilmData -> ListHiddenFilms.hidden.add(data) }, view?.tag.toString())
                }
            }
        })

        return rowView
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

}
