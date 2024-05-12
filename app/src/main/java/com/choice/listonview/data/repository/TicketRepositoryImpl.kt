package com.choice.listonview.data.repository

import com.choice.listonview.core.IResult
import com.choice.listonview.core.IResult.Companion.loading
import com.choice.listonview.core.IResult.Companion.success
import com.choice.listonview.data.dao.TicketDao
import com.choice.listonview.data.entity.TicketEntity
import com.choice.listonview.data.mapping.toDomain
import com.choice.listonview.data.mapping.toEntity
import com.choice.listonview.di.model.Ticket
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.seconds

class TicketRepositoryImpl constructor(
    private val dao: TicketDao,
): TicketRepository {
    override suspend fun getListTickets(): Flow<IResult<List<Ticket>>> {
        return flow {
            dao.getAll().collect { list ->
                if(list.isNotEmpty()) {
                    emit(success(list.map { it.toDomain() }))
                    return@collect
                }

                emit(loading())
            }
        }
    }

    override suspend fun changeTicket(
        id: Int,
        prefix: Int,
        code: Int,
        isDownloaded: Boolean
    ): Flow<IResult<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTickets() {
        dao.deleteAll()
    }

    override suspend fun addTickets() {
        Ticket.EXAMPLE_LIST.map {
            dao.insert(it.toEntity())
        }
    }
}