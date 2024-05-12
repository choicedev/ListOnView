package com.choice.listonview.di

import android.content.Context
import com.choice.listonview.data.repository.TicketRepository
import com.choice.listonview.data.repository.TicketRepositoryImpl
import com.choice.listonview.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        @ApplicationContext app: Context,
        dao: AppDatabase
    ): TicketRepository {
        return TicketRepositoryImpl(app, dao.ticketDao)
    }

}