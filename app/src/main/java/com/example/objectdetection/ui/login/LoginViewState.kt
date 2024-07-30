package com.example.objectdetection.ui.login

import com.example.objectdetection.base.ViewEvent
import com.example.objectdetection.base.ViewState


sealed class LoginViewState : ViewState {
    object RouteHome : LoginViewState()
    data class Error(val message: String) : LoginViewState()
    data class EnableInput(val isEnable: Boolean) : LoginViewState()
    object ShowProgress : LoginViewState()
    object HideProgress : LoginViewState()
}

sealed interface LoginViewEvent : ViewEvent {

    object RouteRegister : LoginViewEvent


}