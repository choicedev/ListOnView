package com.choice.listonview.application

import android.app.Application
import com.choice.listonview.di.usecase.TicketUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltAndroidApp
class ListOnViewApplication : Application() {

    @Inject
    lateinit var useCase: TicketUseCase

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            useCase.removeAllTickets(Unit)
            delay(1000 * 5)
            useCase.addTickets(Unit)
            delay(1000 * 10)
            while (true) {
                delay(10.seconds)
                (1..20).random().let {
                    useCase.updateTicket(it)
                }
            }
        }

    }

}