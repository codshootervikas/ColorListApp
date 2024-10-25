package com.example.colorlistapp

import androidx.lifecycle.LiveData
import com.example.colorlistapp.db.ColorDao
import com.example.colorlistapp.db.ColorEntity
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class ColorRepository @Inject constructor(
    private val colorDao: ColorDao) {
    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("colors")

    val allColors: LiveData<List<ColorEntity>> = colorDao.getAllColors()

    suspend fun insert(color: ColorEntity) {
        colorDao.insertColor(color)
    }

    suspend fun syncColorsToFirebase() {
        val colors = allColors.value ?: return
        for (color in colors) {
            firebaseDatabase.push().setValue(color)
        }
    }
}

