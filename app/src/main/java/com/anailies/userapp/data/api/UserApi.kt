package com.anailies.userapp.data.api

import com.anailies.userapp.data.api.models.adduser.AddUserBody
import com.anailies.userapp.data.api.models.adduser.AddUserResponse
import com.anailies.userapp.data.api.models.deleteuser.DeleteUserResponse
import com.anailies.userapp.data.api.models.getuser.UserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

const val ACCESS_TOKEN = "9da2426bb8fee37ed764e64764ab33aa4e254461ae08834b85627b9f330a2bbc"

interface UserApi {
    @GET("public/v1/users")
    fun getUsers(): Single<UserResponse>

    @GET("public/v1/users")
    fun getUsersByPage(@Query(value = "page") pageNumber: Int): Single<UserResponse>

    @POST("/public/v1/users?access-token=$ACCESS_TOKEN")
    fun addUser(@Body userBody: AddUserBody): Single<Response<AddUserResponse>>

    @DELETE("/public/v1/users/{id}?access-token=$ACCESS_TOKEN")
    fun deleteUser(@Path(value = "id") id: Long): Single<Response<DeleteUserResponse>>
}