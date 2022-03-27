package com.panduprabs.githubusersapi.database

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_users")
data class FavoriteUsers(
    @PrimaryKey
    val id: Int,

    val login: String,
    val avatar_url: String
) : Serializable
