package com.panduprabs.githubusersapi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUsers::class],
    version = 2
)
abstract class DBUsers : RoomDatabase() {
    companion object{
        var INSTANCE : DBUsers? = null

        fun dbConnection(context: Context): DBUsers?{
            if (INSTANCE == null){
                synchronized(DBUsers::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DBUsers::class.java,
                        "db_users"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteUsersDao(): FavoriteUsersDao
}