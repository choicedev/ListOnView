package com.choice.listonview.di.usecase

import com.choice.listonview.core.UseCase
import com.choice.listonview.data.repository.TicketRepository

class RemoveAllTicketsUseCase constructor(
    private val repository: TicketRepository
) : UseCase<Unit, Unit> {
    override suspend fun invoke(input: Unit) {
        repository.deleteAllTickets()
    }

}
