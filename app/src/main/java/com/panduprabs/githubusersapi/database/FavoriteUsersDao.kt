package com.panduprabs.githubusersapi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUsersDao {
    @Insert
    suspend fun addToFavorite(favoriteUsers: FavoriteUsers)

    @Query("SELECT * FROM favorite_users")
    fun getDataFavoriteUsers(): LiveData<List<FavoriteUsers>>

    @Query("DELETE FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun deleteDataFavoriteUsers(id: Int): Int

    @Query("SELECT count(*) FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun checkUsers(id: Int): Int
}