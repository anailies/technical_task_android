package com.anailies.userapp.data.api.models.adduser

import com.google.gson.annotations.SerializedName

data class AddUserErrorData(
    @SerializedName("field")
    val field: String,
    @SerializedName("message")
    val message: String
)