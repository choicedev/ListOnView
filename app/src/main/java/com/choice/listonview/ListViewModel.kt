package com.choice.listonview

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choice.listonview.core.extend.watchStatus
import com.choice.listonview.di.model.Ticket
import com.choice.listonview.di.usecase.GetListAllTicketsUseCase
import com.choice.listonview.di.usecase.TicketUseCase
import com.choice.listonview.ui.model.ListEvent
import com.choice.listonview.ui.model.ListScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val ticketUseCase: TicketUseCase
) : ViewModel() {

    val state = MutableStateFlow(ListScreen())
    private var job: Job? = null
    private val _listState = mutableStateOf(emptyList<Ticket>())
    val listState: State<List<Ticket>> = _listState


    init {
        viewModelScope.launch {
            getListAllTickets()
        }
    }

    private suspend fun getListAllTickets() {
        job?.cancel()
        job = ticketUseCase.getAll(Unit).onEach { result ->
            result.watchStatus(
                success = { list ->
                    state.update {
                        it.copy(event = ListEvent.ListAll(list))
                    }
                },
                loading = {
                    state.update {
                       it.copy(event = ListEvent.Loading)
                    }
                }

            )
        }.launchIn(viewModelScope)
    }

}