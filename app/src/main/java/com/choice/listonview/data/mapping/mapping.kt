package com.choice.listonview.data.mapping

import com.choice.listonview.data.entity.TicketEntity
import com.choice.listonview.di.model.Ticket

fun TicketEntity.toDomain() = Ticket(
    id = id,
    title = title,
    description = description,
    date = date,
    isDownloaded = isDownloaded
)

fun Ticket.toEntity() = TicketEntity(
    id = id,
    title = title,
    description = description,
    date = date,
    isDownloaded = isDownloaded
)
