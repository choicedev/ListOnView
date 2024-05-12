package com.choice.listonview.ui.model

import com.choice.listonview.di.model.Ticket

data class ListScreen(
    val event: ListEvent = ListEvent.Loading,
    val snackBarMessage: Ticket? = null
)