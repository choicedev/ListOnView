package com.choice.listonview.di

import com.choice.listonview.data.repository.TicketRepository
import com.choice.listonview.di.usecase.AddTicketsUseCase
import com.choice.listonview.di.usecase.GetListAllTicketsUseCase
import com.choice.listonview.di.usecase.RemoveAllTicketsUseCase
import com.choice.listonview.di.usecase.TicketUseCase
import com.choice.listonview.di.usecase.UpdateTicketUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {


    @Provides
    @Singleton
    fun provideUseCase(repository: TicketRepository): TicketUseCase {
        return TicketUseCase(
            GetListAllTicketsUseCase(repository),
            RemoveAllTicketsUseCase(repository),
            AddTicketsUseCase(repository),
            UpdateTicketUseCase(repository)
        )
    }


}