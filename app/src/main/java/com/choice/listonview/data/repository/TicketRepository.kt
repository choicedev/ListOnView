package com.choice.listonview.data.repository

import com.choice.listonview.core.IResult
import com.choice.listonview.di.model.Ticket
import kotlinx.coroutines.flow.Flow

interface TicketRepository {

    suspend fun getListTickets() : Flow<IResult<List<Ticket>>>

    suspend fun deleteAllTickets()

    suspend fun addTickets()
    suspend fun updateTicket(id: Int)

}