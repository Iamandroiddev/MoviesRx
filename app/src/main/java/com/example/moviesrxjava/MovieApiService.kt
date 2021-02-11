package com.example.moviesrxjava

import com.example.moviesrxjava.models.Actor
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.models.MovieResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.*

interface MovieApiService {
    @GET("movie/now_playing")
    fun getCurrentlyShowing(@QueryMap queries: HashMap<String, String>): Observable<MovieResponse>

    @GET("movie/popular")
    fun getPopular(@QueryMap queries: HashMap<String, String>): Observable<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcoming(@QueryMap queries: HashMap<String, String>): Observable<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRated(@QueryMap queries: HashMap<String, String>): Observable<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int, @QueryMap queries: HashMap<String, String>): Observable<Movie>

    @GET("movie/{movie_id}/credits")
    fun getCast(@Path("movie_id") id: Int, @QueryMap queries: HashMap<String, String>): Observable<JsonObject>

    @GET("person/{person_id}")
    fun getActorDetails(@Path("person_id") id: Int, @QueryMap queries: HashMap<String, String>): Observable<Actor>

    @GET("person/{person_id}/images")
    fun getActorImages(@Path("person_id") id: Int, @Query("api_key") api: String): Observable<JsonObject>

    @GET("search/movie")
    fun getMoviesBySearch(@QueryMap queries: HashMap<String, String>): Observable<JsonObject>
}