package com.anailies.userapp.data.api.models.getuser

import com.google.gson.annotations.SerializedName

data class UserPagination(
    @SerializedName("total")
    val total: Int,
    @SerializedName("pages")
    val numberOfPages: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("limit")
    val limit: Int
)
