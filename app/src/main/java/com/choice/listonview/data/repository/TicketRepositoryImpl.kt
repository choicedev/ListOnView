package com.choice.listonview.data.repository

import android.content.Context
import android.widget.Toast
import com.choice.listonview.core.IResult
import com.choice.listonview.core.IResult.Companion.loading
import com.choice.listonview.core.IResult.Companion.success
import com.choice.listonview.data.dao.TicketDao
import com.choice.listonview.data.mapping.toDomain
import com.choice.listonview.data.mapping.toEntity
import com.choice.listonview.di.model.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val context: Context,
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

    override suspend fun deleteAllTickets() {
        dao.deleteAll()
    }

    override suspend fun addTickets() {
        Ticket.EXAMPLE_LIST.map {
            dao.insert(it.toEntity())
        }
    }

    override suspend fun updateTicket(id: Int) {
        dao.getById(id)?.let { entity ->
            dao.changeDownloadStatus(id, !entity.isDownloaded)
            val ticket = dao.getById(id)
            updateTicketFlow.emit(ticket?.toDomain())
        }
    }

    companion object {
        var updateTicketFlow = MutableSharedFlow<Ticket?>()
    }
}