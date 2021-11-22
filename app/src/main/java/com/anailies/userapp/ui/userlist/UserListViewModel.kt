package com.anailies.userapp.ui.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anailies.userapp.domain.data.UserRepository
import com.anailies.userapp.domain.entities.User
import com.anailies.userapp.ui.common.LoadState
import com.anailies.userapp.ui.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val disposables = CompositeDisposable()
    val usersList = MutableLiveData<List<User>>()
    val deleteUserEvent = SingleLiveEvent<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val errorText = SingleLiveEvent<String>()

    init {
        getUsers()
    }

    fun deleteUser(id: Long) {
        disposables.add(
            userRepository.deleteUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getUsers()
                    deleteUserEvent.call()
                }, {
                    changeState(LoadState.FAILED)
                })
        )
    }

    private fun getUsers() {
        changeState(LoadState.LOADING)

        disposables.add(
            userRepository
                .getUsersFromLastPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onGetUsersSuccess, ::onError)
        )
    }

    private fun onGetUsersSuccess(userList: List<User>) {
        changeState(LoadState.SUCCESS)
        this.usersList.value = userList
    }

    private fun onError(throwable: Throwable) {
        changeState(LoadState.FAILED)
        throwable.printStackTrace()
    }

    private fun changeState(state: LoadState) {
        when (state) {
            LoadState.SUCCESS -> {
                isLoading.value = false
            }
            LoadState.FAILED -> {
                isLoading.value = false
                errorText.value = GENERIC_ERROR_TEXT
            }
            LoadState.LOADING -> {
                isLoading.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    companion object {
        const val GENERIC_ERROR_TEXT = "Something went wrong"
    }
}