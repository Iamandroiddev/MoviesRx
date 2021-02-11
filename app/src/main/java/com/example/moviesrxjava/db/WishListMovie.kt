package com.example.moviesrxjava.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_table")
class WishListMovie(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var poster_path: String,
    var overview: String,
    var release_date: String,
    var title: String,
    var backdrop_path: String,
    var vote_count: Int,
    var runtime: Int
)