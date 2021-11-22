package com.anailies.userapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anailies.userapp.domain.data.UserRepository
import com.anailies.userapp.domain.entities.User
import com.anailies.userapp.ui.userlist.UserListViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    @Mock
    lateinit var repository: UserRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `assert that user list live data value changes when fetching users`() {
        val users = Single.just(getMockUsers())
        whenever(repository.getUsersFromLastPage()).thenReturn(users)

        val mutableLiveData = UserListViewModel(repository).usersList

        assert(mutableLiveData.value?.size == getMockUsers().size)
    }

    private fun getMockUsers() = listOf(
        User(321, "ds", "ds@aa.com"),
        User(3231, "ds", "d@aa.com")
    )
}
