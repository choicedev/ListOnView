package com.choice.listonview.di.usecase

data class TicketUseCase(
    val getAll: GetListAllTicketsUseCase,
    val removeAllTickets: RemoveAllTicketsUseCase,
    val addTickets: AddTicketsUseCase
)
