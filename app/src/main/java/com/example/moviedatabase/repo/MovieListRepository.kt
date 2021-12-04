package com.example.moviedatabase.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.moviedatabase.ErrorType
import com.example.moviedatabase.Loading
import com.example.moviedatabase.database.Movie
import com.example.moviedatabase.ServiceAndStatus.TmdbService
import com.example.moviedatabase.database.MovieDatabase
import com.example.moviedatabase.database.MovieListDao
import java.lang.Exception
import java.net.UnknownHostException

class MovieListRepository(context: Context) {
    val movieListDao: MovieListDao = MovieDatabase.getDatabase(context).movieListDao()
    private val tmdbService by lazy {
        TmdbService.getInstance()
    }

    fun getMoviesList(): LiveData<List<Movie>> {
        return movieListDao.getMoviesList()
    }

    suspend fun fetchMovies() =

            try {
                val response = tmdbService.getMovies()
                if (response.isSuccessful) {
                    val movies = response.body()
                    movies?.let {
                        movieListDao.insertMovie(it.results)
                    }
                    Loading.success()
                } else {

                    Loading.error(ErrorType.DATA_OFF, null)
                }
            } catch (e: UnknownHostException) {
                Loading.error(ErrorType.NETWORK_ERROR, e.message);
            } catch (e: Exception) {
                Loading.error(ErrorType.UNKNOWN_ERROR, e.message)
            }

    fun deleteAll() {
        movieListDao.deleteAll()
    }
}

