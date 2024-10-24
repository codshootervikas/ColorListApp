package com.example.colorlistapp

import androidx.lifecycle.LiveData
import com.example.colorlistapp.db.ColorDao
import com.example.colorlistapp.db.ColorEntity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow

class ColorRepository(private val colorDao: ColorDao, private val firebaseRepository: FirebaseRepository) {

    fun getAllColors(): Flow<List<ColorEntity>> {
        return colorDao.getAllColors()
    }

    suspend fun insertColor(color: ColorEntity) {
        colorDao.insertColor(color)
        firebaseRepository.syncColorToFirebase(color) // Sync to Firebase
    }

    fun getColorsFromFirebase(): LiveData<List<ColorEntity>> {
        return firebaseRepository.getColorsFromFirebase()
    }
}
