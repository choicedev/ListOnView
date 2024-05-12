package com.choice.listonview.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.choice.listonview.data.dao.TicketDao
import com.choice.listonview.data.entity.TicketEntity
import com.choice.listonview.database.AppDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [TicketEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val ticketDao: TicketDao

    companion object {
        const val DATABASE_NAME: String = "ticket_db"
        const val DATABASE_VERSION: Int = 3
    }
}