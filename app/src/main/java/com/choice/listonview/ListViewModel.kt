package com.choice.listonview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choice.listonview.core.extend.watchStatus
import com.choice.listonview.data.repository.TicketRepository
import com.choice.listonview.data.repository.TicketRepositoryImpl
import com.choice.listonview.di.model.Ticket
import com.choice.listonview.di.usecase.TicketUseCase
import com.choice.listonview.ui.model.ListEvent
import com.choice.listonview.ui.model.ListScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

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
            observerUpdateTicket()
        }
    }


    fun observerUpdateTicket() {
        TicketRepositoryImpl.updateTicketFlow.onEach { ticketFlow ->
            ticketFlow?.let { updatedTicket ->
                val currentList = _listState.value.toMutableList()
                val existingTicketIndex = currentList.indexOfFirst { it.id == updatedTicket.id }

                if (existingTicketIndex >= 0) {
                    // Atualiza o ticket existente
                    currentList[existingTicketIndex] = updatedTicket
                } else {
                    // Remove o ticket da lista, pois não existe mais no banco
                    currentList.add(updatedTicket)
                }

                // Atualiza o estado com a nova lista
                _listState.value = currentList
                state.update {
                    it.copy(snackBarMessage = updatedTicket)
                }

            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getListAllTickets() {
        delay(15.seconds)
        ticketUseCase.getAll(Unit).collect {
            it.watchStatus(
                success = { list ->
                    _listState.value = list
                    state.update { screen ->
                        screen.copy(event = ListEvent.ListAll)
                    }
                },
                loading = {
                    state.update { screen ->
                        screen.copy(event = ListEvent.Loading)
                    }
                }
            )
        }
    }


}