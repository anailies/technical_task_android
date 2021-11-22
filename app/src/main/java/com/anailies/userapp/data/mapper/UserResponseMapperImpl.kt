package com.anailies.userapp.data.mapper

import com.anailies.userapp.data.api.models.adduser.AddUserErrorData
import com.anailies.userapp.data.api.models.getuser.UserResponse
import com.anailies.userapp.domain.entities.User
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject

class UserResponseMapperImpl @Inject constructor() : UserResponseMapper {

    override fun mapToUsers(userResponse: UserResponse): List<User> {
        return userResponse.data.map {
            User(
                id = it.id,
                email = it.email,
                name = it.name
            )
        }
    }

    override fun mapToAddUserErrorData(errorBody: String): AddUserErrorData {
        val data = JSONObject(errorBody).getJSONArray("data")
        return Gson().fromJson(data.get(0).toString(), AddUserErrorData::class.java)
    }
}