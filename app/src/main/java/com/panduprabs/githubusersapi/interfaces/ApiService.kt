package com.panduprabs.githubusersapi.interfaces

import com.panduprabs.githubusersapi.models.DetailUserResponses
import com.panduprabs.githubusersapi.models.User
import com.panduprabs.githubusersapi.models.UsersResponses
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(@Path("username") username: String): Call<DetailUserResponses>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(@Path("username") username: String): Call<List<User>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUsers(@Query("q") username: String): Call<UsersResponses>
}