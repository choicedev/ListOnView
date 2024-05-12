package com.choice.listonview.di

import com.choice.listonview.data.dao.TicketDao
import com.choice.listonview.data.repository.TicketRepository
import com.choice.listonview.data.repository.TicketRepositoryImpl
import com.choice.listonview.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        dao: AppDatabase
    ): TicketRepository {
        return TicketRepositoryImpl(dao.ticketDao)
    }

}