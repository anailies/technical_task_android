package com.anailies.userapp.data.api.models.adduser

import com.google.gson.annotations.SerializedName

data class AddUserBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("status")
    val status: String
)