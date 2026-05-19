package com.schwagersoft.ngamingcase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, INTENT, EVENT>(
    initialState: STATE
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _event = Channel<EVENT>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    protected fun updateState(reduce: STATE.() -> STATE) {
        _state.update { it.reduce() }
    }

    protected fun sendEvent(event: EVENT) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    open fun onIntent(intent: INTENT) {}
}
