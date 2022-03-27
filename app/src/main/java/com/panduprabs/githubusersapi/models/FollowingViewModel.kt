package com.panduprabs.githubusersapi.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panduprabs.githubusersapi.config.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowers = MutableLiveData<List<User>>()

    fun setListFollowing(username: String){
        ApiConfig
            .getApiService()
            .getUserFollowing(username)
            .enqueue(object : Callback<List<User>>{
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }else{
                        Log.d("error", "data null")
                    }
                }
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.d("error", "failed")
                }

            })
//
    }

    fun getListFollowing(): LiveData<List<User>> {
        return listFollowers
    }
}
