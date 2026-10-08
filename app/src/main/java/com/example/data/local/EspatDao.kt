package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EspatDao {
    @Query("SELECT * FROM participants WHERE id = :id LIMIT 1")
    suspend fun getParticipantById(id: String): ParticipantEntity?

    @Query("SELECT * FROM participants ORDER BY score DESC, timeSeconds ASC, mistakes ASC, completedAt ASC")
    fun getAllParticipants(): Flow<List<ParticipantEntity>>

    @Query("SELECT * FROM participants WHERE period = :period ORDER BY score DESC, timeSeconds ASC, mistakes ASC, completedAt ASC")
    fun getParticipantsByPeriod(period: String): Flow<List<ParticipantEntity>>

    @Query("SELECT * FROM participants WHERE period = :period ORDER BY score DESC, timeSeconds ASC, mistakes ASC, completedAt ASC")
    suspend fun getParticipantsByPeriodSync(period: String): List<ParticipantEntity>

    @Query("SELECT * FROM participants ORDER BY score DESC, timeSeconds ASC, mistakes ASC, completedAt ASC")
    suspend fun getAllParticipantsSync(): List<ParticipantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipant(participant: ParticipantEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipants(participants: List<ParticipantEntity>)

    @Query("DELETE FROM participants WHERE id = :id")
    suspend fun deleteParticipant(id: String)

    @Query("DELETE FROM participants")
    suspend fun clearAllParticipants()

    @Query("SELECT * FROM participants WHERE redemptionCode = :code LIMIT 1")
    suspend fun getParticipantByRedemptionCode(code: String): ParticipantEntity?

    @Query("SELECT COUNT(*) FROM participants WHERE period = :period AND redemptionStatus = 'RETIRADO'")
    fun getRedeemedCountByPeriod(period: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM participants WHERE redemptionStatus = 'RETIRADO'")
    fun getTotalRedeemedCount(): Flow<Int>

    @Query("SELECT * FROM campaign_settings WHERE id = 1 LIMIT 1")
    fun getSettings(): Flow<CampaignSettingsEntity?>

    @Query("SELECT * FROM campaign_settings WHERE id = 1 LIMIT 1")
    suspend fun getSettingsSync(): CampaignSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: CampaignSettingsEntity)
}
