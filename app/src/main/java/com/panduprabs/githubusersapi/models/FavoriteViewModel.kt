package com.panduprabs.githubusersapi.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.panduprabs.githubusersapi.database.DBUsers
import com.panduprabs.githubusersapi.database.FavoriteUsers
import com.panduprabs.githubusersapi.database.FavoriteUsersDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUsersDao?
    private var dbUsers: DBUsers?

    init {
        dbUsers = DBUsers.dbConnection(application)
        userDao = dbUsers?.favoriteUsersDao()
    }

    fun getDataFavoriteUser(): LiveData<List<FavoriteUsers>>?{
        return userDao?.getDataFavoriteUsers()
    }
}