package com.anailies.userapp.domain.data

import com.anailies.userapp.domain.entities.User
//import io.reactivex.Completable
//import io.reactivex.Single
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun getUsersFromLastPage(): Single<List<User>>

    fun addUser(email: String, name: String): Completable

    fun deleteUser(id: Long): Completable
}