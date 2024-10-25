package com.example.colorlistapp.db

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "colors")
data class ColorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val colorCode: String,
    val timestamp: Long
)

@Dao
interface ColorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ColorEntity)

    @Query("SELECT * FROM colors")
    fun getAllColors(): LiveData<List<ColorEntity>>

    @Query("DELETE FROM colors")
    suspend fun deleteAllColors()
}

@Database(entities = [ColorEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @SuppressLint("RestrictedApi")
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "color_database"
                )
                    .fallbackToDestructiveMigration() // Optional: clears and rebuilds DB on schema change
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
