package com.anailies.userapp.ui.adduser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anailies.userapp.domain.usecases.AddUserUseCase
import com.anailies.userapp.ui.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
) : ViewModel() {
    private val disposable = CompositeDisposable()

    val userName = MutableLiveData("")
    val userEmail = MutableLiveData("")
    val errorText = MutableLiveData<String>()
    val addUserSuccess = SingleLiveEvent(false)

    fun addUser() {
        disposable.add(
            addUserUseCase.addUser(userEmail.value, userName.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onSuccess, ::onError)
        )
    }

    private fun onSuccess() {
        addUserSuccess.value = true
    }

    private fun onError(throwable: Throwable) {
        errorText.value = throwable.message
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}