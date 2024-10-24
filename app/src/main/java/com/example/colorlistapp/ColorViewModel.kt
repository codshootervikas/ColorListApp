package com.example.colorlistapp

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.colorlistapp.db.ColorDatabase
import com.example.colorlistapp.db.ColorEntity
import kotlinx.coroutines.launch
import kotlin.random.Random

class ColorViewModel(application: Application) : AndroidViewModel(application) {

    private val colorDao = ColorDatabase.getDatabase(application).colorDao()
    private val firebaseRepo = FirebaseRepository()
    private val repository = ColorRepository(colorDao, firebaseRepo)

    val allColors: LiveData<List<ColorEntity>> = repository.getAllColors().asLiveData()

    val firebaseColors: LiveData<List<ColorEntity>> = repository.getColorsFromFirebase()

    fun addColor() {
        val randomColor = ColorEntity(
            colorHex = getRandomColor(),
            timestamp = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.insertColor(randomColor)
        }
    }

    private fun getRandomColor(): String {
        val rnd = Random(6)
        val color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        return String.format("#%06X", (0xFFFFFF and color))
    }
}


