package com.example.moviedatabase.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDetailDao {
    @Query("SELECT * FROM Movies WHERE `id`=:id")
    fun getMovieByID(id: Long): LiveData<Movie>
}