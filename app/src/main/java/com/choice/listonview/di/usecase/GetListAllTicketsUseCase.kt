package com.choice.listonview.di.usecase

import com.choice.listonview.core.IResult
import com.choice.listonview.core.UseCase
import com.choice.listonview.data.repository.TicketRepository
import com.choice.listonview.di.model.Ticket
import kotlinx.coroutines.flow.Flow

class GetListAllTicketsUseCase constructor(
    private val repository: TicketRepository
) : UseCase<Unit, Flow<IResult<List<Ticket>>>> {
    override suspend fun invoke(input: Unit): Flow<IResult<List<Ticket>>> {
        return repository.getListTickets()
    }
}