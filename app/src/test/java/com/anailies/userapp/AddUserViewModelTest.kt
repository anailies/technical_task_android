package com.anailies.userapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anailies.userapp.domain.usecases.AddUserUseCase
import com.anailies.userapp.ui.adduser.AddUserViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AddUserViewModelTest {

    @Mock
    lateinit var addUserUseCase: AddUserUseCase

    lateinit var addUserViewModel: AddUserViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        addUserViewModel = AddUserViewModel(addUserUseCase)
    }

    @Test
    fun `assert that error is set when fields are invalid`() {
        whenever(addUserUseCase.addUser(any(), any()))
            .thenReturn(Completable.error(Throwable("Please enter valid data")))

        addUserViewModel.addUser()

        Assert.assertEquals(addUserViewModel.errorText.value, "Please enter valid data")
    }

    @Test
    fun `assert that addUserSuccess is true when user is successfully added2`() {
        whenever(addUserUseCase.addUser(any(), any()))
            .thenReturn(Completable.complete())

        addUserViewModel.addUser()

        Assert.assertEquals(addUserViewModel.addUserSuccess.value, true)
    }
}