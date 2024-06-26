package com.example.objectdetection.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.objectdetection.base.BaseViewModel
import com.example.objectdetection.data.repo.FirebaseRepository
import com.example.objectdetection.ext.ioScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    val inputEmailStateFlow: MutableStateFlow<String?> = MutableStateFlow("")
    val inputPasswordStateFlow: MutableStateFlow<String?> = MutableStateFlow("")


    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            onChangedViewState(LoginViewState.ShowProgress)
            onChangedViewState(LoginViewState.EnableInput(false))

            val checkEmail = async { checkEmail() }
            val checkPassword = async { checkPassword() }

            checkUser(checkEmail.await(), checkPassword.await())?.let { person ->
                firebaseRepository.login(person.email, person.password)
                    .addOnSuccessListener {
                        onChangedViewState(LoginViewState.RouteHome)
                        onChangedViewState(LoginViewState.HideProgress)
                    }.addOnFailureListener {
                        onChangedViewState(LoginViewState.Error("로그인을 실패하였습니다."))
                        onChangedViewState(LoginViewState.HideProgress)
                    }
            }
        }
    }


    fun register() {
        onChangedViewEvent(LoginViewEvent.RouteRegister)
    }


    private fun checkUser(
        checkEmail: Boolean,
        checkPassword: Boolean
    ): Person? {
        return if (checkEmail && checkPassword) {
            Person(
                inputEmailStateFlow.value!!,
                inputPasswordStateFlow.value!!
            )
        } else {
            onChangedViewState(LoginViewState.EnableInput(true))
            onChangedViewState(LoginViewState.HideProgress)
            null
        }
    }

    private fun checkEmail(): Boolean {
        return when {
            inputEmailStateFlow.value.isNullOrEmpty() -> {
                onChangedViewState(LoginViewState.Error("이메일을 입력해주세요."))
                false
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmailStateFlow.value).matches() -> {
                onChangedViewState(LoginViewState.Error("이메일 형식이 올바르지 않습니다."))
                false
            }

            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            inputPasswordStateFlow.value.isNullOrEmpty() -> {
                onChangedViewState(LoginViewState.Error("비밀번호를 입력해주세요."))
                false
            }

            else -> true
        }
    }

    data class Person(
        val email: String,
        val password: String
    )
}