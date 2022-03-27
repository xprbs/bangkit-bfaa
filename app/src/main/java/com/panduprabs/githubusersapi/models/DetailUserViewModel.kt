package com.panduprabs.githubusersapi.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panduprabs.githubusersapi.config.ApiConfig
import com.panduprabs.githubusersapi.database.DBUsers
import com.panduprabs.githubusersapi.database.FavoriteUsers
import com.panduprabs.githubusersapi.database.FavoriteUsersDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponses>()
    private var userDao: FavoriteUsersDao?
    private var dbUsers: DBUsers?

    init {
        dbUsers = DBUsers.dbConnection(application)
        userDao = dbUsers?.favoriteUsersDao()
    }

    fun setUserDetail(username: String){
        ApiConfig.getApiService()
            .getDetailUsers(username)
            .enqueue(object : Callback<DetailUserResponses>{
                override fun onResponse(
                    call: Call<DetailUserResponses>,
                    response: Response<DetailUserResponses>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponses>, t: Throwable) {
                    Log.d("Gagal dimuat", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponses>{
        return user
    }

    fun addUsersToFavorite(id: Int, username: String, avatar: String){
        CoroutineScope(Dispatchers.IO).launch {
            var dataUsers = FavoriteUsers(id, username, avatar)
            userDao?.addToFavorite(dataUsers)
        }
    }

    suspend fun checkUsers(id: Int) = userDao?.checkUsers(id)

    fun deleteFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteDataFavoriteUsers(id)
        }
    }

}