package com.example.moviesrxjava.models

import java.util.*

data class MovieResponse(
     var page: Int = 0, private var total_pages: Int = 0,
     var total_results: Int = 0,
     val results: ArrayList<Movie>? = null
)
