package com.example.moviesrxjava.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesrxjava.db.WishListMovie
import com.example.moviesrxjava.repository.Repository

class WishListViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private var wishListMoviesList: LiveData<List<WishListMovie>>? = null


    init {

        this.wishListMoviesList = repository.wishList

    }

    fun getWishListMoviesList(): LiveData<List<WishListMovie>>? {
        return wishListMoviesList
    }

    fun clearWishList() {
        repository.clearWishList()
    }
}