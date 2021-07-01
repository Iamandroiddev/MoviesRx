package com.example.moviesrxjava.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

object Constants {
    const val API_KEY = "19cc511b297f733789a2a3bf0bc6a3b3"
    const val BaseURL = "https://api.themoviedb.org/3/"
    const val ImageBaseURL = "https://image.tmdb.org/t/p/original"
    const val ImageBaseURLw780 = "https://image.tmdb.org/t/p/w780"
    const val ImageBaseURLw500 = "https://image.tmdb.org/t/p/w500"
    const val DataBaseName = "WishListDB"
    const val Attribution = "This product uses the TMDb API but is not endorsed or certified by TMDb."
    const val Popular = "Popular"
    const val Upcoming = "Upcoming"
    const val Current = "Current"
    const val TopRated = "TopRated"

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun isNetworkAvailable(context: Context): Boolean {
        val mConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Test for connection
        val mCapabilities =
            mConnectivityManager.getNetworkCapabilities(mConnectivityManager.activeNetwork)
        return mCapabilities != null &&
                (mCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        mCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
    val genreMap: HashMap<Int, String>
        get() {
            val genreMap = HashMap<Int, String>()
            genreMap[28] = "Action"
            genreMap[12] = "Adventure"
            genreMap[16] = "Animation"
            genreMap[35] = "Comedy"
            genreMap[80] = "Crime"
            genreMap[99] = "Documentary"
            genreMap[18] = "Drama"
            genreMap[10751] = "Family"
            genreMap[14] = "Fantasy"
            genreMap[36] = "History"
            genreMap[27] = "Horror"
            genreMap[10402] = "Music"
            genreMap[9648] = "Mystery"
            genreMap[10749] = "Romance"
            genreMap[878] = "Science Fiction"
            genreMap[53] = "Thriller"
            genreMap[10752] = "War"
            genreMap[37] = "Western"
            genreMap[10770] = "TV Movie"
            return genreMap
        }
}