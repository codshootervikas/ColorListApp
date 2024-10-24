package com.example.colorlistapp

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.colorlistapp.db.ColorEntity
import kotlinx.coroutines.launch
import kotlin.random.Random

class ColorViewModel(private val repository: ColorRepository) : ViewModel() {
    val colors: LiveData<List<ColorEntity>> = repository.allColors
    private val _syncCount = MutableLiveData<Int>()
    val syncCount: LiveData<Int> get() = _syncCount

    fun addColor() {
        val colorCode = generateRandomColor()
        val timestamp = System.currentTimeMillis()
        val newColor = ColorEntity(colorCode = colorCode, timestamp = timestamp)
        viewModelScope.launch {
            repository.insert(newColor)
        }
    }

    fun syncColors() {
        viewModelScope.launch {
            repository.syncColorsToFirebase()
            // Update sync count logic here
        }
    }

    private fun generateRandomColor(): /*String*/ {
        // Generate a random hex color code
       /* val random = Random()*/
       /* return String.format("#%06X", random.nextInt(0xFFFFFF + 1))8*/
    }
}


