package com.example.moviedatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_0_1 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Movies ADD COLUMN vote 'FLOAT' NOT NULL DEFAULT ''")
    }
}
@TypeConverters(DbTypeConverter::class)
@Database(entities = [Movie::class], version = 2)
 abstract class MovieDatabase :RoomDatabase() {



        abstract fun movieListDao(): MovieListDao
        abstract fun movieDetailDao(): MovieDetailDao

        companion object {
            @Volatile
            private var instance: MovieDatabase? = null

            fun getDatabase(context: Context) = instance
                    ?: synchronized(this) {
                        Room.databaseBuilder(
                                context.applicationContext,
                                MovieDatabase::class.java,
                                "movie_database"
                        ).addMigrations(MIGRATION_0_1).build().also { instance = it }
                    }
        }
    }
