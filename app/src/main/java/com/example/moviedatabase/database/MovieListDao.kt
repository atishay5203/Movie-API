package com.example.moviedatabase.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieListDao {
    @Query("SELECT * FROM Movies ORDER BY releaseDate DESC")
    fun getMoviesList(): LiveData<List<Movie>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: List<Movie>)
    @Query("DELETE FROM Movies")
    fun deleteAll()
}