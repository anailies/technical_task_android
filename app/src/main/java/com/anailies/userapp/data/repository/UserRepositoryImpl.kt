package com.anailies.userapp.data.repository

import com.anailies.userapp.data.api.UserApi
import com.anailies.userapp.data.api.models.adduser.AddUserBody
import com.anailies.userapp.data.api.models.adduser.AddUserResponse
import com.anailies.userapp.data.api.models.deleteuser.DeleteUserResponse
import com.anailies.userapp.data.api.models.getuser.UserResponse
import com.anailies.userapp.data.mapper.UserResponseMapper
import com.anailies.userapp.domain.data.UserRepository
import com.anailies.userapp.domain.entities.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

const val SUCCESS_ADD_USER = 201
const val SUCCESS_DELETE_USER = 204
const val GENERIC_ERROR = "Something went wrong"

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val mapper: UserResponseMapper,
) : UserRepository {

    override fun getUsersFromLastPage(): Single<List<User>> {
        return getPageCount().flatMap(::getUsersByPage).map(mapper::mapToUsers)
    }

    override fun addUser(email: String, name: String): Completable {
        return api.addUser(
            AddUserBody(
                email = email,
                name = name,
                status = "active",
                gender = "female"
            )
        )
            .flatMapCompletable(::handleAddUserResponse)
            .onErrorResumeNext {
                it.printStackTrace()
                return@onErrorResumeNext if (it is Exception) {
                    Completable.error(Throwable(GENERIC_ERROR))
                } else {
                    Completable.error(it)
                }
            }
    }

    override fun deleteUser(id: Long): Completable {
        return api.deleteUser(id).flatMapCompletable(::handleDeleteUserResponse)
    }

    private fun handleDeleteUserResponse(response: Response<DeleteUserResponse>): Completable {
        return if (response.code() == SUCCESS_DELETE_USER) {
            Completable.complete()
        } else {
            Completable.error(Throwable(GENERIC_ERROR))
        }
    }

    private fun handleAddUserResponse(response: Response<AddUserResponse>): Completable {
        return if (response.code() == SUCCESS_ADD_USER) {
            Completable.complete()
        } else {
            response.errorBody()?.string()?.let { body ->
                val error = mapper.mapToAddUserErrorData(body)
                Completable.error(Throwable("${error.field} ${error.message}"))
            } ?: Completable.error(Throwable(GENERIC_ERROR))
        }
    }

    private fun getPageCount(): Single<Int> {
        return api.getUsers().map { it.meta.pagination.numberOfPages }
    }

    private fun getUsersByPage(pageNumber: Int): Single<UserResponse> {
        return api.getUsersByPage(pageNumber)
    }
}