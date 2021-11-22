package com.anailies.userapp.data.mapper

import com.anailies.userapp.data.api.models.adduser.AddUserErrorData
import com.anailies.userapp.data.api.models.getuser.UserResponse
import com.anailies.userapp.domain.entities.User

interface UserResponseMapper {
    fun mapToUsers(userResponse: UserResponse) : List<User>

    fun mapToAddUserErrorData(errorBody: String): AddUserErrorData
}