package com.choice.listonview.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.choice.listonview.core.BaseDao
import com.choice.listonview.data.entity.TicketEntity
import com.choice.listonview.data.entity.TicketEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao : BaseDao<TicketEntity> {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Flow<List<TicketEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getById(id: Int): TicketEntity

    @Query("UPDATE $TABLE_NAME SET isDownloaded = :isDownload, isChanged = 1 WHERE id = :id")
    fun changeDownloadStatus(id: Int, isDownload: Boolean)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()

}