package com.anailies.userapp.data.api.models.getuser

import com.google.gson.annotations.SerializedName

class UserMeta(
    @SerializedName("pagination")
    val pagination: UserPagination
)