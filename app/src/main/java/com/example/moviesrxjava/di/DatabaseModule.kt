package com.example.moviesrxjava.di

import android.app.Application
import androidx.room.Room
import com.example.moviesrxjava.db.MovieDatabase
import com.example.moviesrxjava.db.WishListDao
import com.example.moviesrxjava.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(application: Application?): MovieDatabase {
        return Room.databaseBuilder(
            application!!,
            MovieDatabase::class.java,
            Constants.DataBaseName
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideWishListDao(movieDatabase: MovieDatabase): WishListDao {
        return movieDatabase.wishListDao()
    }
}
