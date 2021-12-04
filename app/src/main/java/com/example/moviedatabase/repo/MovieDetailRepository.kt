package com.example.moviedatabase.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.moviedatabase.database.Movie
import com.example.moviedatabase.database.MovieDatabase
import com.example.moviedatabase.database.MovieDetailDao


class MovieDetailRepository(context: Context) {
    private val movieDetailDao: MovieDetailDao = MovieDatabase.getDatabase(context).movieDetailDao()
    fun getMovies(id: Long): LiveData<Movie> {
        return movieDetailDao.getMovieByID(id)
    }

}