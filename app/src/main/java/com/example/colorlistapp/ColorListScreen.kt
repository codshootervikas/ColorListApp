package com.example.colorlistapp

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.colorlistapp.db.ColorEntity


@Composable
fun ColorListScreen(viewModel: ColorViewModel = ColorViewModel(application = Application()) ) {
    val allColors by viewModel.allColors.observeAsState(emptyList())
    val firebaseColors by viewModel.firebaseColors.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(allColors) { colorEntity ->
                ColorItem(colorEntity)
            }
        }
        Button(onClick = { viewModel.addColor() }) {
            Text(text = "Add Random Color")
        }
    }
}

@Composable
fun ColorItem(colorEntity: ColorEntity) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(android.graphics.Color.parseColor(colorEntity.colorHex)))
    )
}

