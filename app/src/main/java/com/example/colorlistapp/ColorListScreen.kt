package com.example.colorlistapp

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Colors
import com.example.colorlistapp.db.ColorEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorListScreen(viewModel: ColorViewModel) {
    // Observe the colors and sync count
    val colors by viewModel.colors.observeAsState(emptyList())
    val syncCount by viewModel.syncCount.observeAsState(0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Colors List") },

                )
        },
        floatingActionButton = {
            // Row to hold both buttons
            Row {
                // Add Color Button
                FloatingActionButton(
                    onClick = { viewModel.addColor() },
                ) {
                    Icon(Icons.Default.Sync, contentDescription = "Add Color")
                }

                // Sync Button with Badge
                FloatingActionButton(
                    onClick = { viewModel.syncColors() },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    BadgeBox(badgeContent = { Text(syncCount.toString()) }) {
                        Icon(Icons.Default.Sync, contentDescription = "Sync")
                    }
                }
            }
        }
    ) { innerPadding ->
        // Color List
        LazyColumn(contentPadding = innerPadding) {
            items(colors) { color ->
                ColorItem(color)
            }
        }
    }
}

@Composable
fun BadgeBox(badgeContent: @Composable () -> Unit, content: @Composable () -> Unit) {

}

@Composable
fun ColorItem(color: ColorEntity) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(android.graphics.Color.parseColor(color.colorCode)))
            .padding(8.dp)
    ) {
        Text(
            text = color.colorCode,
            color = Color.White,
        )
        Text(
            text = formatTimestamp(color.timestamp), // A function to format the timestamp
            color = Color.White,
        )
    }
}

fun formatTimestamp(timestamp: Long): String {
    // Format the timestamp to a readable date string if needed
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}


