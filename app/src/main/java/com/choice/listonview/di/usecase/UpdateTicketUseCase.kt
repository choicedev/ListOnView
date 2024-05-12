package com.choice.listonview.di.usecase

import com.choice.listonview.core.UseCase
import com.choice.listonview.data.repository.TicketRepository

class UpdateTicketUseCase constructor(
    private val repository: TicketRepository
): UseCase<Int, Unit> {
    override suspend fun invoke(input: Int) {
        repository.updateTicket(input)
    }

}
