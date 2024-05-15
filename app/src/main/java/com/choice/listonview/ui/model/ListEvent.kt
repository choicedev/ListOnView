package com.choice.listonview.ui.model

import com.choice.listonview.di.model.Ticket

sealed class ListEvent {

    data object ListAll : ListEvent()
    data object Loading : ListEvent()

}