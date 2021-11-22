package com.anailies.userapp.di

import com.anailies.userapp.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class UserApiModule {

    @Provides
    fun providesUsersApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}