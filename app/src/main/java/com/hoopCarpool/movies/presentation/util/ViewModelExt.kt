package com.hoopCarpool.movies.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopCarpool.util.Event
import com.hoopCarpool.util.EventBus
import kotlinx.coroutines.launch

fun ViewModel.sendEvent(event: Event){
    viewModelScope.launch {
        EventBus.sendEvent(event)
    }
}