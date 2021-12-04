package com.example.moviedatabase.MovieDetail

import android.app.Application
import androidx.lifecycle.*
import com.example.moviedatabase.database.Movie
import com.example.moviedatabase.repo.MovieDetailRepository

class MovieDetailViewModel(context: Application) : AndroidViewModel(context) {
    val repo: MovieDetailRepository = MovieDetailRepository(context)
    val _id = MutableLiveData<Long>(0)
    val id: LiveData<Long>
        get() = _id

    fun setId(id: Long) {
        if (_id.value != id)
            _id.value = id
    }

    val movie: LiveData<Movie> = Transformations.switchMap(_id)
    {
        repo.getMovies(it)
    }

}