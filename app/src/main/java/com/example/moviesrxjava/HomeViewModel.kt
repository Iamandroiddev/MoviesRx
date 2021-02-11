package com.example.moviesrxjava

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesrxjava.db.WishListMovie
import com.example.moviesrxjava.models.Actor
import com.example.moviesrxjava.models.Cast
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.repository.Repository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class HomeViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {
    private val currentMoviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData<ArrayList<Movie>>()
    private val popularMoviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData<ArrayList<Movie>>()
    private val topRatedMoviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData<ArrayList<Movie>>()
    private val upcomingMoviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData<ArrayList<Movie>>()
    private val queriesMovies: MutableLiveData<ArrayList<Movie>> = MutableLiveData<ArrayList<Movie>>()
    private val movieCastList: MutableLiveData<ArrayList<Cast>> = MutableLiveData<ArrayList<Cast>>()
    private val movieDetails: MutableLiveData<Movie> = MutableLiveData<Movie>()
    private val actorDetails: MutableLiveData<Actor> = MutableLiveData<Actor>()
    private val disposables = CompositeDisposable()
    val currentlyShowingList: MutableLiveData<ArrayList<Movie>>
        get() = currentMoviesList

    fun getPopularMoviesList(): MutableLiveData<ArrayList<Movie>> {
        return popularMoviesList
    }

    fun getTopRatedMoviesList(): MutableLiveData<ArrayList<Movie>> {
        return topRatedMoviesList
    }

    fun getUpcomingMoviesList(): MutableLiveData<ArrayList<Movie>> {
        return upcomingMoviesList
    }

    fun getMovie(): MutableLiveData<Movie> {
        return movieDetails
    }

    fun getActor(): MutableLiveData<Actor> {
        return actorDetails
    }

    fun getMovieCastList(): MutableLiveData<ArrayList<Cast>> {
        return movieCastList
    }

    fun getQueriesMovies(): MutableLiveData<ArrayList<Movie>> {
        return queriesMovies
    }

    fun getCurrentlyShowingMovies(map: HashMap<String, String>) {
        disposables.add(repository.getCurrentlyShowing(map)
                .subscribeOn(Schedulers.io())
                .map { movieResponse -> movieResponse.results }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArrayList<Movie>>() {
                    override fun onNext(movies: @NonNull ArrayList<Movie>?) {
                        currentMoviesList.setValue(movies)
                    }

                    override fun onError(e: @NonNull Throwable?) {}
                    override fun onComplete() {}
                })
        )
    }


    fun getPopularMovies(map: HashMap<String, String>) {
        disposables.add(repository.getPopular(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> popularMoviesList.setValue(result.results) }
                ) { error -> Log.e(TAG, "getPopularMovies: " + error.message) }
        )
    }

    fun getTopRatedMovies(map: HashMap<String, String>) {
        disposables.add(repository.getTopRated(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> topRatedMoviesList.setValue(result.results) }
                ) { error -> Log.e(TAG, "getTopRated: " + error.message) }
        )
    }

    fun getUpcomingMovies(map: HashMap<String, String>) {
        disposables.add(repository.getUpcoming(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> upcomingMoviesList.setValue(result.results) }
                ) { error -> Log.e(TAG, "getUpcoming: " + error.message) }
        )
    }

    fun getMovieDetails(movieId: Int, map: HashMap<String, String>) {
        disposables.add(repository.getMovieDetails(movieId, map)
                .subscribeOn(Schedulers.io())
                .map({ movie ->
                    val genreNames = ArrayList<String>()
                    // MovieResponse gives list of genre(object) so we will map each id to it genre name here.a
                    for (genre in movie.genres!!) {
                        genreNames.add(genre.name.toString())
                    }
                    movie.genre_names
                    movie
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> movieDetails.setValue(result) }
                ) { error -> Log.e(TAG, "getMovieDetails: " + error.message) }
        )
    }

    fun getCast(movieId: Int, map: HashMap<String, String>) {
        disposables.add(repository.getCast(movieId, map)
                .subscribeOn(Schedulers.io())
                .map({ jsonObject ->
                    val jsonArray = jsonObject.getAsJsonArray("cast")
                    Gson().fromJson<ArrayList<Cast>>(jsonArray.toString(), object : TypeToken<ArrayList<Cast>>() {}.type)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> movieCastList.setValue(result) }
                ) { error -> Log.e(TAG, "getCastList: " + error.message) }
        )
    }

    fun getActorDetails(personId: Int, map: HashMap<String, String>) {
        disposables.add(repository.getActorDetails(personId, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> actorDetails.setValue(result) }
                ) { error -> Log.e(TAG, "getActorDetails: " + error.message) }
        )
    }

    fun getQueriedMovies(map: HashMap<String, String>) {
        disposables.add(repository.getMoviesBySearch(map)
                .subscribeOn(Schedulers.io())
                .map({ jsonObject ->
                    val jsonArray = jsonObject.getAsJsonArray("results")
                    Gson().fromJson<ArrayList<Movie>>(jsonArray.toString(),
                            object : TypeToken<ArrayList<Movie?>?>() {}.type)
                }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> queriesMovies.setValue(result) }
                ) { error -> Log.e(TAG, "getPopularMovies: " + error.message) }
        )
    }

    // room methods
    fun insertMovie(wishListMovie: WishListMovie) {
        Log.e(TAG, "insertMovie: ")
        repository.insertMovie(wishListMovie)
    }

    fun deleteMovie(movieId: Int) {
        repository.deleteMovie(movieId)
    }

    fun getWishListMovie(movieId: Int): WishListMovie {
        return repository.getWishListMovie(movieId)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}