package com.example.objectdetection.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun ViewModel.uiScope(block: suspend CoroutineScope.() -> Unit): Job {
    runBlocking { }
    return viewModelScope.launch(Dispatchers.Main) {
        block()
    }
}

fun ViewModel.ioScope(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}

fun ViewModel.defaultScope(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Default) {
        block()
    }
}