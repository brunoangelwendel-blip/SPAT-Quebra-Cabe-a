package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ParticipantEntity::class, CampaignSettingsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EspatDatabase : RoomDatabase() {
    abstract fun espatDao(): EspatDao

    companion object {
        @Volatile
        private var INSTANCE: EspatDatabase? = null

        fun getDatabase(context: Context): EspatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EspatDatabase::class.java,
                    "espat_puzzle_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
