package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campaign_settings")
data class CampaignSettingsEntity(
    @PrimaryKey
    val id: Int = 1,
    val isOpen: Boolean = true,
    val isFrozen: Boolean = false,
    val startDate: String = "16/09/2026",
    val endDate: String = "18/09/2026",
    val endTime: String = "22:30",
    val quotaMorning: Int = 67,
    val quotaAfternoon: Int = 67,
    val quotaNight: Int = 66,
    val totalCups: Int = 200
)
