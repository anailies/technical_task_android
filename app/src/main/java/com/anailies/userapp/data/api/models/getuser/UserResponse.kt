package com.anailies.userapp.data.api.models.getuser

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("meta")
    val meta: UserMeta,
    @SerializedName("data")
    val data: List<UserData>
)