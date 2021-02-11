package com.example.moviesrxjava.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.moviesrxjava.MovieApiService
import com.example.moviesrxjava.db.WishListDao
import com.example.moviesrxjava.db.WishListMovie
import com.example.moviesrxjava.models.Actor
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.models.MovieResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import java.util.*
import javax.inject.Inject

    class Repository @Inject constructor(private val apiService: MovieApiService, private val wishListDao: WishListDao) {
        fun getCurrentlyShowing(map: HashMap<String, String>): Observable<MovieResponse> {
            return apiService.getCurrentlyShowing(map)
        }

        fun getPopular(map: HashMap<String, String>): Observable<MovieResponse> {
            return apiService.getPopular(map)
        }

        fun getTopRated(map: HashMap<String, String>): Observable<MovieResponse> {
            return apiService.getTopRated(map)
        }

        fun getUpcoming(map: HashMap<String, String>): Observable<MovieResponse> {
            return apiService.getUpcoming(map)
        }

        fun getMovieDetails(movieId: Int, map: HashMap<String, String>): Observable<Movie> {
            return apiService.getMovieDetails(movieId, map)
        }

        fun getCast(movieId: Int, map: HashMap<String, String>): Observable<JsonObject> {
            return apiService.getCast(movieId, map)
        }

        fun getActorDetails(personId: Int, map: HashMap<String, String>): Observable<Actor> {
            return apiService.getActorDetails(personId, map)
        }

        fun getMoviesBySearch(map: HashMap<String, String>): Observable<JsonObject> {
            return apiService.getMoviesBySearch(map)
        }

        fun insertMovie(wishListMovie: WishListMovie) {
            Log.e(TAG, "insertMovie: ")
            wishListDao.insert(wishListMovie)
        }

        fun deleteMovie(movieId: Int) {
            wishListDao.delete(movieId)
        }

        fun clearWishList() {
            wishListDao.clearWishList()
        }

        val wishList: LiveData<List<WishListMovie>>
            get() = wishListDao.getWishList()

        fun getWishListMovie(movieId: Int): WishListMovie {
            return wishListDao.getWishListMovie(movieId)
        }

        companion object {
            private const val TAG = "Repository"
        }

    }
