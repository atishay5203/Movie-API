package com.example.moviedatabase.MovieList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedatabase.Loading
import com.example.moviedatabase.database.Movie
import com.example.moviedatabase.repo.MovieListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(context: Application) : AndroidViewModel(context) {
    private val repo: MovieListRepository = MovieListRepository(context)
    val movieList: LiveData<List<Movie>>
        get() = repo.getMoviesList()

    val _status: MutableLiveData<Loading> = MutableLiveData(Loading.success())
    val status: LiveData<Loading>
        get() = _status

    fun fetchFromNetwork() {
        _status.value = Loading.loading()
        viewModelScope.launch {
            _status.value =
                    withContext(Dispatchers.IO) {

                        repo.fetchMovies()
                    }


        }
    }

    fun refresh() {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                repo.deleteAll()
            }
            fetchFromNetwork()
        }
    }
}