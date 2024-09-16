package com.example.presentation.ui.register

import androidx.lifecycle.viewModelScope
import com.example.domain.repo.FirebaseRepository
import com.example.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseRepository: com.example.domain.repo.FirebaseRepository
) : BaseViewModel() {


    val inputEmailStateFlow: MutableStateFlow<String?> = MutableStateFlow("")
    val inputPasswordStateFlow: MutableStateFlow<String?> = MutableStateFlow("")
    val inputPasswordOkStateFlow: MutableStateFlow<String?> = MutableStateFlow("")

    fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            onChangedViewState(RegisterViewState.ShowProgress)
            onChangedViewState(RegisterViewState.EnableInput(false))
            val checkEmail = async { checkEmail() }
            val checkPassword = async { checkPassword() }
            val checkPasswordOk = async { checkPasswordOk() }

            checkUser(
                checkEmail.await(),
                checkPassword.await(),
                checkPasswordOk.await()
            )?.let { person ->
                firebaseRepository.register(
                    person.email,
                    person.password
                ).addOnSuccessListener {
                    onChangedViewState(RegisterViewState.RouteHome)
                    onChangedViewState(RegisterViewState.HideProgress)
                }.addOnFailureListener {
                    onChangedViewState(RegisterViewState.Error("회원가입을 실패하였습니다."))
                    onChangedViewState(RegisterViewState.EnableInput(true))
                    onChangedViewState(RegisterViewState.HideProgress)
                }
            }
        }
    }


    private fun checkUser(
        checkEmail: Boolean,
        checkPassword: Boolean,
        checkPasswordOk: Boolean,
    ): Person? {
        return if (checkEmail && checkPassword && checkPasswordOk) {
            Person(inputEmailStateFlow.value!!, inputPasswordStateFlow.value!!)
        } else {
            onChangedViewState(RegisterViewState.EnableInput(true))
            onChangedViewState(RegisterViewState.HideProgress)
            null
        }
    }

    private fun checkEmail(): Boolean {
        return when {
            inputEmailStateFlow.value.isNullOrEmpty() -> {
                onChangedViewState(RegisterViewState.Error("이메일을 입력해 주세요."))
                false
            }

            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            inputPasswordStateFlow.value.isNullOrEmpty() -> {
                onChangedViewState(RegisterViewState.Error("비밀번호를 입력해 주세요."))
                false
            }

            else -> true
        }
    }

    private fun checkPasswordOk(): Boolean {
        return when {
            inputPasswordStateFlow.value != inputPasswordOkStateFlow.value -> {
                onChangedViewState(RegisterViewState.Error("비밀번호 재입력을 올바르게 입력해 주세요."))
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