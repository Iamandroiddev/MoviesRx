package com.example.moviesrxjava.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WishListMovie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    // public abstract MovieDao movieDao();
    abstract fun wishListDao(): WishListDao
}