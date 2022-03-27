package com.panduprabs.githubusersapi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailUserResponses(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("login")
	val username: String? = null,

	@field:SerializedName("avatar_url")
	val avatar: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("public_repos")
	val repository: Int? = null,

	@field:SerializedName("followers")
	val totalFollowers: Int? = 0,

	@field:SerializedName("following")
	val totalFollowing: Int? = 0,

	@field:SerializedName("followers_url")
	val followers: String? = null,

	@field:SerializedName("following_url")
	val following: String? = null
) : Parcelable
