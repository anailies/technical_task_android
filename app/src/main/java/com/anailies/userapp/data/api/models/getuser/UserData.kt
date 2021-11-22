package com.anailies.userapp.data.api.models.getuser

import com.google.gson.annotations.SerializedName

class UserData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("status")
    val status: String
)
