package com.anailies.userapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anailies.userapp.data.api.UserApi
import com.anailies.userapp.data.api.models.adduser.AddUserResponse
import com.anailies.userapp.data.mapper.UserResponseMapper
import com.anailies.userapp.data.repository.SUCCESS_ADD_USER
import com.anailies.userapp.data.repository.UserRepositoryImpl
import com.anailies.userapp.domain.data.UserRepository
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Protocol
import okhttp3.Request
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @Mock
    lateinit var userApi: UserApi

    @Mock
    lateinit var userResponseMapper: UserResponseMapper

    lateinit var repository: UserRepository

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        repository = UserRepositoryImpl(userApi, userResponseMapper)
    }

    @Test
    fun `assert error message when add user api error`() {
        whenever(userApi.addUser(any()))
            .thenReturn(Single.error(Throwable(ADD_USER_ERROR_MESSAGE)))

        repository.addUser("email@test.com", "test")
            .test()
            .assertError { it.message == ADD_USER_ERROR_MESSAGE }
    }

    @Test
    fun `assert success when add user success`() {
        whenever(userApi.addUser(any()))
            .thenReturn(Single.just(getMockAddUserResponse()))

        repository.addUser("email@test.com", "test")
            .test()
            .assertComplete()
    }

    private fun getMockAddUserResponse() =
        Response.success(
            AddUserResponse(
                "test",
                "test@email.com",
                "female",
                "active"
            ),
            okhttp3.Response.Builder()
                .code(SUCCESS_ADD_USER)
                .protocol(Protocol.HTTP_1_1)
                .message("OK")
                .request(Request.Builder().url("http://localhost/").build())
                .build())

    companion object {
        const val ADD_USER_ERROR_MESSAGE = "Something went wrong"
    }
}