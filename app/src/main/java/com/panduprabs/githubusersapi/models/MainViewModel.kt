package com.panduprabs.githubusersapi.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panduprabs.githubusersapi.config.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val list = MutableLiveData<List<User>>()

    fun setSearchUser(query: String){
        ApiConfig.getApiService()
            .searchUsers(query)
            .enqueue(object : Callback<UsersResponses>{
                override fun onResponse(
                    call: Call<UsersResponses>,
                    response: Response<UsersResponses>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UsersResponses>, t: Throwable) {
                    Log.d("error -> ", t.message.toString())
                }

            })
    }

    fun getSearchUser(): LiveData<List<User>>{
        return list
    }
}