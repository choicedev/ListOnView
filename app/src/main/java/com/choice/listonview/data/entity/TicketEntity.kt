package com.choice.listonview.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.choice.listonview.data.entity.TicketEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val isDownloaded: Boolean = false,
){

    companion object {
        const val TABLE_NAME = "ticket"
    }

}