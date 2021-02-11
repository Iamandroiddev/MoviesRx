package com.example.moviesrxjava.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WishListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wishListMovie: WishListMovie)

    @Query("DELETE From wishlist_table WHERE id = :movieId")
    fun delete(movieId: Int)

    @Query("DELETE FROM wishlist_table")
    fun clearWishList()

    @Query("SELECT * FROM wishlist_table")
    fun getWishList(): LiveData<List<WishListMovie>>

    @Query("SELECT * FROM wishlist_table WHERE id = :movieId ")
    fun getWishListMovie(movieId: Int): WishListMovie
}
