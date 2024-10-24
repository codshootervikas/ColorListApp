package com.example.colorlistapp.db

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "colors")
data class ColorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val colorHex: String,
    val timestamp: Long
)

@Dao
interface ColorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ColorEntity)

    @Query("SELECT * FROM colors ORDER BY timestamp DESC")
    fun getAllColors(): Flow<List<ColorEntity>>

    @Delete
    suspend fun deleteColor(color: ColorEntity)
}

@Database(entities = [ColorEntity::class], version = 1)
abstract class ColorDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao

    companion object {
        @Volatile
        private var INSTANCE: ColorDatabase? = null

        fun getDatabase(@SuppressLint("RestrictedApi") context: Application): ColorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ColorDatabase::class.java,
                    "color_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
