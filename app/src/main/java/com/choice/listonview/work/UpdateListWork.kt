package com.choice.listonview.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.choice.listonview.di.usecase.TicketUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltWorker
class UpdateListWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    @Inject
    lateinit var useCase: TicketUseCase

    override suspend fun doWork(): Result {
        delay(TimeUnit.SECONDS.toMillis(30))



        return Result.success()
    }
}