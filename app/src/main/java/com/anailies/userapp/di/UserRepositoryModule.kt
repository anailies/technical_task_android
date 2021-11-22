package com.anailies.userapp.di

import com.anailies.userapp.data.repository.UserRepositoryImpl
import com.anailies.userapp.domain.data.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    abstract fun bindsUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository
}