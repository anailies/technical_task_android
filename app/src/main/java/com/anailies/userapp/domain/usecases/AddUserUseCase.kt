package com.anailies.userapp.domain.usecases

import com.anailies.userapp.domain.data.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

const val INVALID_DATA_ERROR = "Please enter valid data"

class AddUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun addUser(email: String?, name: String?): Completable {
        if (!email.isNullOrEmpty() && !name.isNullOrEmpty()) {
            return userRepository.addUser(email, name)
        }
        return Completable.error(Throwable(INVALID_DATA_ERROR))
    }
}