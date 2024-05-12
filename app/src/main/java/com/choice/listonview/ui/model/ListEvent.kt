package com.choice.listonview.ui.model

import com.choice.listonview.di.model.Ticket

sealed class ListEvent {

    data class ListAll(val list: List<Ticket>) : ListEvent()
    data object Loading : ListEvent()

}