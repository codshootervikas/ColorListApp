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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ColorViewModel @Inject constructor(private val repository: ColorRepository) : ViewModel() {
    val colors: LiveData<List<ColorEntity>> = repository.allColors
    private val _syncCount = MutableLiveData<Int>()
    val syncCount: LiveData<Int> get() = _syncCount

    fun addColor() {
        val colorCode = getRandomColorHex()
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

    fun getRandomColorHex(): String {
        val red = Random.nextInt(0, 256)
        val green = Random.nextInt(0, 256)
        val blue = Random.nextInt(0, 256)

        return String.format("#%02X%02X%02X", red, green, blue)
    }
}


