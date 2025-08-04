package com.example.demo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val time: String = "",
    val isDone: Boolean = false,
    val status: String = "To-Do", // "To-Do", "Progress", "Done"
    val category: String = "General",
    val progress: Int = 0, // 0..100
    val date: String = "" // Format "yyyy-MM-dd"
)

