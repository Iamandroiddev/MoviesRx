package com.example.moviesrxjava.models

import com.google.gson.JsonObject
import java.util.*

data class Movie(
     var poster_path: String? = null,
     var overview: String? = null,
     var release_date: String? = null,
     var title: String? = null,
     var backdrop_path: String? = null ,
     val id: Int? = null,
     var vote_count: Int? = null,
     var runtime: Int? = null,
     val popularity: Number? = null,
     var vote_average: Number? = null,
     val genre_ids: ArrayList<Int>? = null,
     val genre_names: ArrayList<String>? = null ,
     val genres: ArrayList<Genre>? = null,
    val videos: JsonObject? = null
)
