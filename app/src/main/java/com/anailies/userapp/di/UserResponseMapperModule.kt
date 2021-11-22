package com.anailies.userapp.di

import com.anailies.userapp.data.mapper.UserResponseMapper
import com.anailies.userapp.data.mapper.UserResponseMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserResponseMapperModule {

    @Binds
    abstract fun bindsUserResponseMapper(mapper: UserResponseMapperImpl): UserResponseMapper
}