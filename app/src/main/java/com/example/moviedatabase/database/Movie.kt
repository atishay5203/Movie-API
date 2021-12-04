package com.example.moviedatabase.database

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName="Movies")
data class Movie(
        @PrimaryKey(autoGenerate = true) val id:Long,

        @ColumnInfo(name="title")
        @SerializedName("title")
        val title:String,

        @ColumnInfo(name="releaseDate")
        @SerializedName("release_date")
        val releaseDate :Date ,

        @ColumnInfo(name="backdrop")
        @SerializedName("backdrop_path")
        val backdrop: String
        ,
        @ColumnInfo(name="poster")
        @SerializedName("poster_path")
        val poster:String ,

        @ColumnInfo(name="details")
        @SerializedName("overview")
        val details:String,
        @ColumnInfo(name="vote")
        @SerializedName("vote_average")
        val votes:Float


)
